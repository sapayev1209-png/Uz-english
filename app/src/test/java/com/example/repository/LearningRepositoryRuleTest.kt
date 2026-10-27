package com.example.repository

import com.example.base.FirestoreEmulatorTestBase
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.UUID

class LearningRepositoryRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun getOrCreateProfile_and_updateProfileSettings_succeedsForOwner() = runBlocking {
        val uid = signInTestUser(ALICE_EMAIL)
        val repo = LearningRepository(firestore)

        val profileResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.getOrCreateProfile(displayName = "Nilufar", email = ALICE_EMAIL)
        }
        assertTrue(profileResult.isSuccess)
        val profile = profileResult.getOrThrow()
        assertEquals(uid, profile.userId)

        val updateResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.updateProfileSettings(
                displayName = "Nilufar Usmanova",
                cefrLevel = "B1",
                learningGoal = "IELTS va akademik o'qish",
                dailyGoalMinutes = 30L,
                placementCompleted = true,
                placementScore = 75L
            )
        }
        assertTrue(updateResult.isSuccess)
    }

    @Test
    fun vocabAndLessonAndQuizProgress_ownerCrudAndFlowObservation_succeeds() = runBlocking {
        val uid = signInTestUser(ALICE_EMAIL)
        val repo = LearningRepository(firestore)
        val wordId = "w_" + UUID.randomUUID().toString().replace("-", "").take(12)
        val lessonId = "les_" + UUID.randomUUID().toString().replace("-", "").take(12)
        val attemptId = "att_" + UUID.randomUUID().toString().replace("-", "").take(12)

        val saveVocab = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.saveVocabProgress(
                wordId = wordId,
                english = "Opportunity",
                uzbek = "Imkoniyat",
                learned = true,
                favorite = true,
                masteryBox = 3L,
                nextReviewEpochDay = 20750L
            )
        }
        assertTrue(saveVocab.isSuccess)

        val emittedVocab = withTimeout(FLOW_TIMEOUT_MS) {
            repo.observeVocabProgress(uid).first { list -> list.any { it.wordId == wordId } }
        }
        assertTrue(emittedVocab.any { it.wordId == wordId && it.learned })

        val saveLesson = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.saveLessonProgress(
                lessonId = lessonId,
                skillType = "GRAMMAR",
                title = "Present Simple",
                score = 90L
            )
        }
        assertTrue(saveLesson.isSuccess)

        val recordQuiz = withTimeout(DEFAULT_TIMEOUT_MS) {
            repo.recordQuizAttempt(
                attemptId = attemptId,
                lessonId = lessonId,
                questionId = "q_1",
                skillType = "GRAMMAR",
                submittedAnswer = "He works every day",
                isCorrect = true,
                score = 100L,
                errorCategory = "NONE",
                feedbackUzbek = "To'g'ri! 3-shaxs birlikda -s qo'shimchasi qo'shiladi."
            )
        }
        assertTrue(recordQuiz.isSuccess)
    }

    @Test
    fun crossUserAccess_readingAnotherUserProfile_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        val aliceRepo = LearningRepository(firestore)
        withTimeout(DEFAULT_TIMEOUT_MS) {
            aliceRepo.getOrCreateProfile("Alice", ALICE_EMAIL).getOrThrow()
        }

        signInTestUser(BOB_EMAIL)
        val bobRepo = LearningRepository(firestore)
        val crossReadResult = withTimeout(DEFAULT_TIMEOUT_MS) {
            bobRepo.getProfileForUser(aliceUid)
        }
        assertTrue(crossReadResult.isFailure)
        val ex = crossReadResult.exceptionOrNull()?.unwrapFirestoreException()
        assertNotNull(ex)
        assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, ex?.code)
    }

    @Test
    fun unauthenticatedUser_observingVocabProgress_failsWithPermissionDenied() = runBlocking {
        val aliceUid = signInTestUser(ALICE_EMAIL)
        auth.signOut()
        val repo = LearningRepository(firestore)

        try {
            withTimeout(FLOW_TIMEOUT_MS) {
                repo.observeVocabProgress(aliceUid).first()
            }
            fail("Expected exception for unauthenticated observation")
        } catch (e: Exception) {
            val firestoreEx = e.unwrapFirestoreException()
            assertNotNull("Expected nested FirebaseFirestoreException, got $e", firestoreEx)
            assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreEx?.code)
        }
    }

    private companion object {
        const val ALICE_EMAIL = "alice@uzenglish.test"
        const val BOB_EMAIL = "bob@uzenglish.test"
        const val DEFAULT_TIMEOUT_MS = 5000L
        const val FLOW_TIMEOUT_MS = 3000L
    }
}
