package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiTutorService
import com.example.data.GrammarAndSkillsCatalog
import com.example.data.VocabularyCatalog
import com.example.model.AiSpeakingFeedback
import com.example.model.AiWritingReview
import com.example.model.ChatMessage
import com.example.model.LessonProgress
import com.example.model.QuizAttemptRecord
import com.example.model.QuizQuestion
import com.example.model.TutorContext
import com.example.model.UiState
import com.example.model.UserProfile
import com.example.model.VocabProgress
import com.example.model.VocabWord
import com.example.repository.LearningRepository
import com.example.repository.LocalAppPreferences
import com.example.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "UzEnglishVM"

class UzEnglishViewModel(
    private val repository: LearningRepository,
    private val preferencesRepository: PreferencesRepository,
    private val aiService: GeminiTutorService,
    private val currentUserId: String,
    private val initialDisplayName: String,
    private val initialEmail: String
) : ViewModel() {

    val appPreferences: StateFlow<LocalAppPreferences> = preferencesRepository.preferencesFlow
        .catch { emit(LocalAppPreferences()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LocalAppPreferences()
        )

    val profileState: StateFlow<UiState<UserProfile>> = repository.observeUserProfile(currentUserId)
        .map<UserProfile?, UiState<UserProfile>> { profile ->
            if (profile != null) {
                UiState.Success(profile)
            } else {
                UiState.Loading
            }
        }
        .catch { e ->
            Log.w(TAG, "Error observing profile", e)
            emit(UiState.Error(e.localizedMessage ?: "Profilni yuklashda xatolik"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Loading
        )

    val vocabProgressList: StateFlow<List<VocabProgress>> = repository.observeVocabProgress(currentUserId)
        .catch { e ->
            Log.w(TAG, "Error observing vocab progress", e)
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val lessonProgressList: StateFlow<List<LessonProgress>> = repository.observeLessonProgress(currentUserId)
        .catch { e ->
            Log.w(TAG, "Error observing lesson progress", e)
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val quizAttemptsList: StateFlow<List<QuizAttemptRecord>> = repository.observeQuizAttempts(currentUserId)
        .catch { e ->
            Log.w(TAG, "Error observing quiz attempts", e)
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _tutorContext = MutableStateFlow(TutorContext())
    val tutorContext: StateFlow<TutorContext> = _tutorContext.asStateFlow()

    private val _isTutorOpen = MutableStateFlow(false)
    val isTutorOpen: StateFlow<Boolean> = _isTutorOpen.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "welcome_msg",
                isUser = false,
                text = "Assalomu alaykum! Men **UstozBot** — sizning shaxsiy AI ingliz tili yordamchingizman. Grammatika qoidalari, so'z ma'nolari yoki mashqlar bo'yicha istalgan savolingizni bering!"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isTutorLoading = MutableStateFlow(false)
    val isTutorLoading: StateFlow<Boolean> = _isTutorLoading.asStateFlow()

    private val _writingReviewState = MutableStateFlow<UiState<AiWritingReview>>(UiState.Idle)
    val writingReviewState: StateFlow<UiState<AiWritingReview>> = _writingReviewState.asStateFlow()

    private val _speakingFeedbackState = MutableStateFlow<UiState<AiSpeakingFeedback>>(UiState.Idle)
    val speakingFeedbackState: StateFlow<UiState<AiSpeakingFeedback>> = _speakingFeedbackState.asStateFlow()

    private val _grammarAiExplanationState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val grammarAiExplanationState: StateFlow<UiState<String>> = _grammarAiExplanationState.asStateFlow()

    private val _extraQuestionsByLesson = MutableStateFlow<Map<String, List<QuizQuestion>>>(emptyMap())
    val extraQuestionsByLesson: StateFlow<Map<String, List<QuizQuestion>>> = _extraQuestionsByLesson.asStateFlow()

    private val _statusBannerMessage = MutableStateFlow<String?>(null)
    val statusBannerMessage: StateFlow<String?> = _statusBannerMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getOrCreateProfile(
                displayName = initialDisplayName.ifBlank { "O'quvchi" },
                email = initialEmail
            )
        }
    }

    fun clearStatusBanner() {
        _statusBannerMessage.value = null
    }

    fun updateTutorContext(
        screenName: String,
        lessonTitle: String = "",
        currentQuestion: String = "",
        userDraftAnswer: String = ""
    ) {
        val currentLevel = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
        _tutorContext.value = TutorContext(
            screenName = screenName,
            lessonTitle = lessonTitle,
            currentQuestion = currentQuestion,
            userLevel = currentLevel,
            userDraftAnswer = userDraftAnswer
        )
    }

    fun setTutorOpen(open: Boolean) {
        _isTutorOpen.value = open
    }

    fun sendTutorMessage(messageText: String) {
        val cleanText = messageText.trim()
        if (cleanText.isBlank() || _isTutorLoading.value) return

        val userMsg = ChatMessage(
            id = "u_${System.currentTimeMillis()}",
            isUser = true,
            text = cleanText
        )
        _chatMessages.value = _chatMessages.value + userMsg
        _isTutorLoading.value = true

        viewModelScope.launch {
            val currentLevel = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
            val ctx = _tutorContext.value.copy(userLevel = currentLevel)
            val result = aiService.askTutor(cleanText, ctx)
            _isTutorLoading.value = false
            result.fold(
                onSuccess = { response ->
                    val botMsg = ChatMessage(
                        id = "b_${System.currentTimeMillis()}",
                        isUser = false,
                        text = response.replyUzbekAndEnglish,
                        suggestedActionRoute = response.suggestedActionRoute,
                        suggestedActionLabel = response.suggestedActionLabel
                    )
                    _chatMessages.value = _chatMessages.value + botMsg
                },
                onFailure = { err ->
                    val errMsg = ChatMessage(
                        id = "e_${System.currentTimeMillis()}",
                        isUser = false,
                        text = "Xatolik: ${err.localizedMessage ?: "AI javob bera olmadi."}"
                    )
                    _chatMessages.value = _chatMessages.value + errMsg
                }
            )
        }
    }

    fun completeOnboardingAndPlacement(
        displayName: String,
        learningGoal: String,
        dailyGoalMinutes: Long,
        placementScore: Int,
        estimatedCefr: String
    ) {
        viewModelScope.launch {
            val res = repository.updateProfileSettings(
                displayName = displayName,
                cefrLevel = estimatedCefr,
                learningGoal = learningGoal,
                dailyGoalMinutes = dailyGoalMinutes,
                placementCompleted = true,
                placementScore = placementScore.toLong()
            )
            if (res.isSuccess) {
                repository.saveLessonProgress(
                    lessonId = "placement_test",
                    skillType = "PLACEMENT",
                    title = "Darajani aniqlash testi",
                    score = placementScore.toLong()
                )
                repository.addStudyProgress(xpDelta = 50L, minutesDelta = 5L, lessonsDelta = 1L)
                _statusBannerMessage.value = "Tabriklaymiz! Darajangiz: $estimatedCefr ($placementScore%). Shaxsiy o'quv rejangiz tayyor!"
            }
        }
    }

    fun updateProfileSettings(
        displayName: String,
        cefrLevel: String,
        learningGoal: String,
        dailyGoalMinutes: Long
    ) {
        viewModelScope.launch {
            val current = (profileState.value as? UiState.Success)?.data
            val res = repository.updateProfileSettings(
                displayName = displayName,
                cefrLevel = cefrLevel,
                learningGoal = learningGoal,
                dailyGoalMinutes = dailyGoalMinutes,
                placementCompleted = current?.placementCompleted ?: true,
                placementScore = current?.placementScore ?: 0L
            )
            if (res.isSuccess) {
                _statusBannerMessage.value = "Profil sozlamalari saqlandi!"
            }
        }
    }

    fun toggleWordLearned(word: VocabWord) {
        viewModelScope.launch {
            val existing = vocabProgressList.value.find { it.wordId == word.id }
            val newLearned = !(existing?.learned ?: false)
            val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
            val newBox = if (newLearned) maxOf((existing?.masteryBox ?: 0L) + 1L, 1L).coerceAtMost(5L) else 0L
            val intervalDays = when (newBox) {
                1L -> 1L
                2L -> 3L
                3L -> 7L
                4L -> 14L
                else -> 30L
            }
            repository.saveVocabProgress(
                wordId = word.id,
                english = word.english,
                uzbek = word.uzbek,
                learned = newLearned,
                favorite = existing?.favorite ?: false,
                masteryBox = newBox,
                nextReviewEpochDay = currentEpochDay + intervalDays
            )
            if (newLearned && existing?.learned != true) {
                repository.addStudyProgress(xpDelta = 10L, minutesDelta = 1L, wordsDelta = 1L)
            }
        }
    }

    fun toggleWordFavorite(word: VocabWord) {
        viewModelScope.launch {
            val existing = vocabProgressList.value.find { it.wordId == word.id }
            val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
            repository.saveVocabProgress(
                wordId = word.id,
                english = word.english,
                uzbek = word.uzbek,
                learned = existing?.learned ?: false,
                favorite = !(existing?.favorite ?: false),
                masteryBox = existing?.masteryBox ?: 0L,
                nextReviewEpochDay = existing?.nextReviewEpochDay ?: currentEpochDay
            )
        }
    }

    fun recordSpacedRepetitionReview(word: VocabWord, remembered: Boolean) {
        viewModelScope.launch {
            val existing = vocabProgressList.value.find { it.wordId == word.id }
            val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
            val prevBox = existing?.masteryBox ?: 1L
            val newBox = if (remembered) (prevBox + 1L).coerceAtMost(5L) else 1L
            val intervalDays = when (newBox) {
                1L -> 1L
                2L -> 2L
                3L -> 5L
                4L -> 10L
                else -> 21L
            }
            repository.saveVocabProgress(
                wordId = word.id,
                english = word.english,
                uzbek = word.uzbek,
                learned = remembered || (existing?.learned == true),
                favorite = existing?.favorite ?: false,
                masteryBox = newBox,
                nextReviewEpochDay = currentEpochDay + intervalDays
            )
            repository.addStudyProgress(xpDelta = if (remembered) 8L else 3L, minutesDelta = 1L)
        }
    }

    fun recordQuestionAttempt(
        question: QuizQuestion,
        skillType: String,
        submittedAnswer: String,
        isCorrect: Boolean
    ) {
        viewModelScope.launch {
            val attemptId = "att_${UUID.randomUUID().toString().replace("-", "").take(16)}"
            repository.recordQuizAttempt(
                attemptId = attemptId,
                lessonId = question.lessonId,
                questionId = question.id,
                skillType = skillType,
                submittedAnswer = submittedAnswer,
                isCorrect = isCorrect,
                score = if (isCorrect) 100L else 0L,
                errorCategory = if (isCorrect) "NONE" else question.errorCategory,
                feedbackUzbek = question.explanationUz
            )
        }
    }

    fun completeLessonQuiz(
        lessonId: String,
        skillType: String,
        title: String,
        scorePercentage: Int
    ) {
        viewModelScope.launch {
            val alreadyCompleted = lessonProgressList.value.any { it.lessonId == lessonId && it.completed }
            repository.saveLessonProgress(
                lessonId = lessonId,
                skillType = skillType,
                title = title,
                score = scorePercentage.toLong()
            )
            repository.addStudyProgress(
                xpDelta = (scorePercentage / 2).coerceAtLeast(15).toLong(),
                minutesDelta = 5L,
                lessonsDelta = if (alreadyCompleted) 0L else 1L
            )
            _statusBannerMessage.value = "\"$title\" yakunlandi! Natija: $scorePercentage%"
        }
    }

    fun requestGrammarAiPersonalization(lessonTitle: String, userQuestion: String) {
        viewModelScope.launch {
            _grammarAiExplanationState.value = UiState.Loading
            val level = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
            try {
                aiService.streamGrammarPersonalization(lessonTitle, level, userQuestion).collect { partial ->
                    _grammarAiExplanationState.value = UiState.Success(partial)
                }
            } catch (e: Exception) {
                _grammarAiExplanationState.value = UiState.Error(e.localizedMessage ?: "Tushuntirish olishda xatolik")
            }
        }
    }

    fun generateExtraGrammarQuestion(lessonId: String, lessonTitle: String) {
        viewModelScope.launch {
            val level = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
            val res = aiService.generateExtraQuestion(lessonId, lessonTitle, level)
            res.fold(
                onSuccess = { q ->
                    val currentList = _extraQuestionsByLesson.value[lessonId].orEmpty()
                    _extraQuestionsByLesson.value = _extraQuestionsByLesson.value + (lessonId to (currentList + q))
                    _statusBannerMessage.value = "Yangi AI mashq qo'shildi!"
                },
                onFailure = { err ->
                    _statusBannerMessage.value = err.localizedMessage ?: "Mashq yaratishda xatolik"
                }
            )
        }
    }

    fun submitWritingForAiReview(
        promptId: String,
        promptTitle: String,
        promptInstructions: String,
        userText: String
    ) {
        viewModelScope.launch {
            _writingReviewState.value = UiState.Loading
            val level = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
            val result = aiService.reviewWriting(promptTitle, promptInstructions, level, userText)
            result.fold(
                onSuccess = { review ->
                    _writingReviewState.value = UiState.Success(review)
                    repository.saveLessonProgress(
                        lessonId = promptId,
                        skillType = "WRITING",
                        title = promptTitle,
                        score = review.overallScore.toLong()
                    )
                    repository.recordQuizAttempt(
                        attemptId = "wrt_${System.currentTimeMillis()}",
                        lessonId = promptId,
                        questionId = promptId,
                        skillType = "WRITING",
                        submittedAnswer = userText,
                        isCorrect = review.overallScore >= 60,
                        score = review.overallScore.toLong(),
                        errorCategory = if (review.overallScore >= 75) "NONE" else "Writing & Grammar",
                        feedbackUzbek = review.explanationUzbek
                    )
                    repository.addStudyProgress(xpDelta = 25L, minutesDelta = 6L, lessonsDelta = 1L)
                },
                onFailure = { err ->
                    _writingReviewState.value = UiState.Error(err.localizedMessage ?: "Yozma ishni tekshirishda xatolik")
                }
            )
        }
    }

    fun resetWritingReview() {
        _writingReviewState.value = UiState.Idle
    }

    fun submitSpeakingForAiFeedback(
        scenarioId: String,
        scenarioTitle: String,
        targetPromptEn: String,
        recognizedText: String
    ) {
        viewModelScope.launch {
            _speakingFeedbackState.value = UiState.Loading
            val level = (profileState.value as? UiState.Success)?.data?.cefrLevel ?: "A1"
            val result = aiService.evaluateSpeakingTranscript(scenarioTitle, targetPromptEn, level, recognizedText)
            result.fold(
                onSuccess = { feedback ->
                    _speakingFeedbackState.value = UiState.Success(feedback)
                    val avgScore = (feedback.grammarScore + feedback.vocabularyScore + feedback.clarityAndRelevanceScore) / 3
                    repository.saveLessonProgress(
                        lessonId = scenarioId,
                        skillType = "SPEAKING",
                        title = scenarioTitle,
                        score = avgScore.toLong()
                    )
                    repository.recordQuizAttempt(
                        attemptId = "spk_${System.currentTimeMillis()}",
                        lessonId = scenarioId,
                        questionId = scenarioId,
                        skillType = "SPEAKING",
                        submittedAnswer = recognizedText,
                        isCorrect = avgScore >= 60,
                        score = avgScore.toLong(),
                        errorCategory = if (avgScore >= 75) "NONE" else "Spoken Grammar",
                        feedbackUzbek = feedback.feedbackUzbek
                    )
                    repository.addStudyProgress(xpDelta = 20L, minutesDelta = 4L, lessonsDelta = 1L)
                },
                onFailure = { err ->
                    _speakingFeedbackState.value = UiState.Error(err.localizedMessage ?: "Nutqni tahlil qilishda xatolik")
                }
            )
        }
    }

    fun resetSpeakingFeedback() {
        _speakingFeedbackState.value = UiState.Idle
    }

    fun saveWordFromReadingGlossary(word: String, meaningUz: String, pronunciation: String) {
        viewModelScope.launch {
            val wordId = "read_w_${word.lowercase().replace(Regex("[^a-z0-9]"), "_")}"
            val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
            repository.saveVocabProgress(
                wordId = wordId,
                english = word,
                uzbek = meaningUz,
                learned = true,
                favorite = true,
                masteryBox = 1L,
                nextReviewEpochDay = currentEpochDay + 1L
            )
            repository.addStudyProgress(xpDelta = 5L, minutesDelta = 1L, wordsDelta = 1L)
            _statusBannerMessage.value = "\"$word\" ($meaningUz) shaxsiy lug'atingizga saqlandi!"
        }
    }

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.setDarkTheme(enabled) }
    }

    fun setSlowTtsDefault(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.setSlowTtsDefault(enabled) }
    }

    fun setShowUzbekTranslations(enabled: Boolean) {
        viewModelScope.launch { preferencesRepository.setShowUzbekTranslations(enabled) }
    }

    fun saveRobotPosition(x: Float, y: Float) {
        viewModelScope.launch { preferencesRepository.saveRobotOffset(x, y) }
    }
}
