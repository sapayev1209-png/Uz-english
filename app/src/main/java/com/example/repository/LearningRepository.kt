package com.example.repository

import android.content.Context
import com.example.R
import com.example.firebase.OperationType
import com.example.firebase.handleFirestoreError
import com.example.model.LessonProgress
import com.example.model.QuizAttemptRecord
import com.example.model.UserProfile
import com.example.model.VocabProgress
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LearningRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("Foydalanuvchi tizimga kirmagan (User must be signed in with Google).")
    }

    private fun currentIsoDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    fun observeUserProfile(userId: String = requireUserId()): Flow<UserProfile?> = flow {
        val path = "users/$userId"
        emitAll(
            db.collection("users").document(userId)
                .snapshots()
                .map { snapshot ->
                    if (snapshot.exists()) {
                        snapshot.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    } else {
                        null
                    }
                }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.GET, path)
                    throw error
                }
        )
    }

    suspend fun getOrCreateProfile(
        displayName: String = "O'quvchi",
        email: String = ""
    ): Result<UserProfile> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        return try {
            val snap = docRef.get().await()
            if (snap.exists()) {
                val existing = snap.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    ?: UserProfile(id = uid, userId = uid, displayName = displayName.ifBlank { "O'quvchi" }, email = email)
                Result.success(existing)
            } else {
                val today = currentIsoDate()
                val safeName = displayName.trim().ifBlank { "O'quvchi" }.take(100)
                val safeEmail = email.trim().take(150)
                val payload = mapOf(
                    "userId" to uid,
                    "displayName" to safeName,
                    "email" to safeEmail,
                    "cefrLevel" to "A1",
                    "learningGoal" to "Sayohat va kundalik muloqot",
                    "dailyGoalMinutes" to 15L,
                    "placementCompleted" to false,
                    "placementScore" to 0L,
                    "streakDays" to 1L,
                    "xpPoints" to 0L,
                    "completedLessonsCount" to 0L,
                    "wordsLearnedCount" to 0L,
                    "todayMinutesDone" to 0L,
                    "lastActivityDate" to today,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.set(payload).await()
                val createdSnap = docRef.get().await()
                val created = createdSnap.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    ?: UserProfile(id = uid, userId = uid, displayName = safeName, email = safeEmail, lastActivityDate = today)
                Result.success(created)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun updateProfileSettings(
        displayName: String,
        cefrLevel: String,
        learningGoal: String,
        dailyGoalMinutes: Long,
        placementCompleted: Boolean,
        placementScore: Long
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        return try {
            val snap = docRef.get().await()
            if (!snap.exists()) {
                getOrCreateProfile(displayName = displayName)
            }
            val updates = mapOf(
                "displayName" to displayName.trim().ifBlank { "O'quvchi" }.take(100),
                "cefrLevel" to cefrLevel,
                "learningGoal" to learningGoal.trim().ifBlank { "Umumiy ingliz tili" }.take(100),
                "dailyGoalMinutes" to dailyGoalMinutes.coerceIn(5L, 180L),
                "placementCompleted" to placementCompleted,
                "placementScore" to placementScore.coerceIn(0L, 100L),
                "lastActivityDate" to currentIsoDate(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun addStudyProgress(
        xpDelta: Long,
        minutesDelta: Long,
        lessonsDelta: Long = 0L,
        wordsDelta: Long = 0L
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        return try {
            val snap = docRef.get().await()
            val profile = if (snap.exists()) {
                snap.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
            } else {
                getOrCreateProfile().getOrNull()
            } ?: return Result.failure(IllegalStateException("Profile not found"))

            val today = currentIsoDate()
            val newStreak = if (profile.lastActivityDate.isNotBlank() && profile.lastActivityDate != today) {
                (profile.streakDays + 1L).coerceAtMost(10000L)
            } else {
                profile.streakDays.coerceAtLeast(1L)
            }
            val newMinutes = if (profile.lastActivityDate == today) {
                (profile.todayMinutesDone + minutesDelta).coerceIn(0L, 1440L)
            } else {
                minutesDelta.coerceIn(0L, 1440L)
            }

            val updates = mapOf(
                "xpPoints" to (profile.xpPoints + xpDelta).coerceIn(0L, 10000000L),
                "todayMinutesDone" to newMinutes,
                "completedLessonsCount" to (profile.completedLessonsCount + lessonsDelta).coerceIn(0L, 10000L),
                "wordsLearnedCount" to (profile.wordsLearnedCount + wordsDelta).coerceIn(0L, 50000L),
                "streakDays" to newStreak,
                "lastActivityDate" to today,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeVocabProgress(userId: String = requireUserId()): Flow<List<VocabProgress>> = flow {
        val path = "users/$userId/vocab_progress"
        emitAll(
            db.collection("users").document(userId).collection("vocab_progress")
                .whereEqualTo("userId", userId)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(VocabProgress::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    throw error
                }
        )
    }

    suspend fun saveVocabProgress(
        wordId: String,
        english: String,
        uzbek: String,
        learned: Boolean,
        favorite: Boolean,
        masteryBox: Long,
        nextReviewEpochDay: Long
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("vocab_progress").document(wordId)
        return try {
            val snap = docRef.get().await()
            if (snap.exists()) {
                val updates = mapOf(
                    "english" to english.take(120),
                    "uzbek" to uzbek.take(120),
                    "learned" to learned,
                    "favorite" to favorite,
                    "masteryBox" to masteryBox.coerceIn(0L, 5L),
                    "nextReviewEpochDay" to nextReviewEpochDay.coerceIn(0L, 1000000L),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.update(updates).await()
            } else {
                val payload = mapOf(
                    "userId" to uid,
                    "wordId" to wordId.take(64),
                    "english" to english.take(120),
                    "uzbek" to uzbek.take(120),
                    "learned" to learned,
                    "favorite" to favorite,
                    "masteryBox" to masteryBox.coerceIn(0L, 5L),
                    "nextReviewEpochDay" to nextReviewEpochDay.coerceIn(0L, 1000000L),
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.set(payload).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeLessonProgress(userId: String = requireUserId()): Flow<List<LessonProgress>> = flow {
        val path = "users/$userId/lesson_progress"
        emitAll(
            db.collection("users").document(userId).collection("lesson_progress")
                .whereEqualTo("userId", userId)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(LessonProgress::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    throw error
                }
        )
    }

    suspend fun saveLessonProgress(
        lessonId: String,
        skillType: String,
        title: String,
        score: Long
    ): Result<Unit> {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("lesson_progress").document(lessonId)
        return try {
            val snap = docRef.get().await()
            val clampedScore = score.coerceIn(0L, 100L)
            if (snap.exists()) {
                val prevBest = snap.getLong("bestScore") ?: 0L
                val prevAttempts = snap.getLong("attemptsCount") ?: 1L
                val updates = mapOf(
                    "skillType" to skillType,
                    "title" to title.take(150),
                    "completed" to true,
                    "bestScore" to maxOf(prevBest, clampedScore),
                    "attemptsCount" to (prevAttempts + 1L).coerceAtMost(10000L),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.update(updates).await()
            } else {
                val payload = mapOf(
                    "userId" to uid,
                    "lessonId" to lessonId.take(64),
                    "skillType" to skillType,
                    "title" to title.take(150),
                    "completed" to true,
                    "bestScore" to clampedScore,
                    "attemptsCount" to 1L,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.set(payload).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeQuizAttempts(userId: String = requireUserId()): Flow<List<QuizAttemptRecord>> = flow {
        val path = "users/$userId/quiz_attempts"
        emitAll(
            db.collection("users").document(userId).collection("quiz_attempts")
                .whereEqualTo("userId", userId)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(QuizAttemptRecord::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    throw error
                }
        )
    }

    suspend fun recordQuizAttempt(
        attemptId: String,
        lessonId: String,
        questionId: String,
        skillType: String,
        submittedAnswer: String,
        isCorrect: Boolean,
        score: Long,
        errorCategory: String,
        feedbackUzbek: String
    ): Result<String> {
        val uid = requireUserId()
        val cleanAttemptId = attemptId.replace(Regex("[^a-zA-Z0-9_\\-]"), "_").take(64).ifBlank { "att_${System.currentTimeMillis()}" }
        val docRef = db.collection("users").document(uid).collection("quiz_attempts").document(cleanAttemptId)
        return try {
            val payload = mapOf(
                "userId" to uid,
                "lessonId" to lessonId.take(64).ifBlank { "general" },
                "questionId" to questionId.take(64).ifBlank { "q1" },
                "skillType" to skillType,
                "submittedAnswer" to submittedAnswer.take(2000),
                "isCorrect" to isCorrect,
                "score" to score.coerceIn(0L, 100L),
                "errorCategory" to errorCategory.take(100).ifBlank { "NONE" },
                "feedbackUzbek" to feedbackUzbek.take(2000),
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(cleanAttemptId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun getProfileForUser(targetUserId: String): Result<UserProfile?> {
        val docRef = db.collection("users").document(targetUserId)
        return try {
            val snap = docRef.get().await()
            Result.success(snap.toObject(UserProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.GET, docRef.path)
            Result.failure(e)
        }
    }
}
