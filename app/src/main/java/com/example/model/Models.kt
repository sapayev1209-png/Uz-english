package com.example.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "O'quvchi",
    val email: String = "",
    val cefrLevel: String = "A1",
    val learningGoal: String = "Sayohat va kundalik muloqot",
    val dailyGoalMinutes: Long = 15L,
    val placementCompleted: Boolean = false,
    val placementScore: Long = 0L,
    val streakDays: Long = 1L,
    val xpPoints: Long = 0L,
    val completedLessonsCount: Long = 0L,
    val wordsLearnedCount: Long = 0L,
    val todayMinutesDone: Long = 0L,
    val lastActivityDate: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class VocabProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val wordId: String = "",
    val english: String = "",
    val uzbek: String = "",
    val learned: Boolean = false,
    val favorite: Boolean = false,
    val masteryBox: Long = 0L,
    val nextReviewEpochDay: Long = 0L,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class LessonProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val lessonId: String = "",
    val skillType: String = "GRAMMAR",
    val title: String = "",
    val completed: Boolean = false,
    val bestScore: Long = 0L,
    val attemptsCount: Long = 1L,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class QuizAttemptRecord(
    @DocumentId val id: String = "",
    val userId: String = "",
    val lessonId: String = "",
    val questionId: String = "",
    val skillType: String = "GRAMMAR",
    val submittedAnswer: String = "",
    val isCorrect: Boolean = false,
    val score: Long = 0L,
    val errorCategory: String = "NONE",
    val feedbackUzbek: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

enum class CefrLevel(val code: String, val titleUz: String, val descriptionUz: String) {
    A1("A1", "Boshlang'ich (Beginner)", "Oddiy so'zlar, salomlashish va kundalik iboralar"),
    A2("A2", "Elementar (Elementary)", "Kundalik vaziyatlar, o'tgan va kelasi zamon asoslari"),
    B1("B1", "O'rta (Intermediate)", "Sayohat, ish va o'z fikrini erkin tushuntirish"),
    B2("B2", "Yuqori o'rta (Upper-Intermediate)", "Murakkab matnlar, bahs-munozara va ravon muloqot"),
    C1("C1", "Ilg'or (Advanced)", "Akademik va professional darajada mukammal nutq"),
    C2("C2", "Mukammal (Mastery)", "Ona tilidek erkin va nozik uslubiy ifodalar");

    companion object {
        fun fromCode(code: String): CefrLevel = entries.find { it.code.equals(code, ignoreCase = true) } ?: A1
    }
}

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    FILL_BLANK,
    SENTENCE_ORDER,
    TYPED_ANSWER
}

data class QuizQuestion(
    val id: String,
    val lessonId: String,
    val type: QuestionType,
    val promptEn: String,
    val promptUz: String,
    val options: List<String> = emptyList(),
    val correctAnswer: String,
    val explanationUz: String,
    val errorCategory: String,
    val cefrLevel: String = "A1"
)

data class VocabWord(
    val id: String,
    val english: String,
    val uzbek: String,
    val pronunciation: String,
    val exampleEn: String,
    val exampleUz: String,
    val topic: String,
    val cefrLevel: String = "A1"
)

data class GrammarExample(
    val english: String,
    val uzbek: String
)

data class GrammarMistake(
    val wrongEn: String,
    val correctEn: String,
    val reasonUz: String
)

data class GrammarLesson(
    val id: String,
    val cefrLevel: String,
    val titleEn: String,
    val titleUz: String,
    val explanationUz: String,
    val formula: String,
    val affirmativeForm: String,
    val negativeForm: String,
    val questionForm: String,
    val examples: List<GrammarExample>,
    val commonMistakes: List<GrammarMistake>,
    val guidedPracticeHintUz: String,
    val questions: List<QuizQuestion>
)

data class ListeningExercise(
    val id: String,
    val cefrLevel: String,
    val titleEn: String,
    val titleUz: String,
    val transcriptEn: String,
    val summaryUz: String,
    val questions: List<QuizQuestion>
)

data class SpeakingScenario(
    val id: String,
    val cefrLevel: String,
    val titleEn: String,
    val titleUz: String,
    val situationUz: String,
    val targetPhrasesEn: List<String>,
    val samplePromptEn: String,
    val samplePromptUz: String,
    val sampleResponseEn: String
)

data class WordMeaning(
    val word: String,
    val meaningUz: String,
    val pronunciation: String
)

data class ReadingPassage(
    val id: String,
    val cefrLevel: String,
    val titleEn: String,
    val titleUz: String,
    val passageEn: String,
    val translationUz: String,
    val glossary: List<WordMeaning>,
    val questions: List<QuizQuestion>
)

data class WritingPrompt(
    val id: String,
    val cefrLevel: String,
    val category: String,
    val titleUz: String,
    val promptEn: String,
    val instructionsUz: String,
    val targetKeywords: List<String>,
    val sampleAnswerEn: String
)

data class AiWritingReview(
    val originalText: String,
    val correctedText: String,
    val grammarScore: Int,
    val vocabularyScore: Int,
    val coherenceScore: Int,
    val overallScore: Int,
    val explanationUzbek: String,
    val keyMistakesUzbek: List<String>,
    val suggestionsUzbek: List<String>
)

data class AiSpeakingFeedback(
    val recognizedText: String,
    val correctedSentenceEn: String,
    val grammarScore: Int,
    val vocabularyScore: Int,
    val clarityAndRelevanceScore: Int,
    val feedbackUzbek: String,
    val pronunciationTipUzbek: String
)

data class TutorContext(
    val screenName: String = "Bosh sahifa",
    val lessonTitle: String = "",
    val currentQuestion: String = "",
    val userLevel: String = "A1",
    val userDraftAnswer: String = ""
)

data class ChatMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val suggestedActionRoute: String? = null,
    val suggestedActionLabel: String? = null
)

sealed interface UiState<out T> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
