package com.example.ai

import android.util.Log
import com.example.model.AiSpeakingFeedback
import com.example.model.AiWritingReview
import com.example.model.QuizQuestion
import com.example.model.QuestionType
import com.example.model.TutorContext
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import org.json.JSONObject

private const val TAG = "GeminiTutorService"

data class TutorChatResponse(
    val replyUzbekAndEnglish: String,
    val suggestedActionRoute: String? = null,
    val suggestedActionLabel: String? = null
)

class GeminiTutorService {

    private var requestCountInWindow = 0
    private var windowStartMillis = System.currentTimeMillis()

    private fun checkRateLimit(): Boolean {
        val now = System.currentTimeMillis()
        if (now - windowStartMillis > 60_000L) {
            windowStartMillis = now
            requestCountInWindow = 0
        }
        if (requestCountInWindow >= 15) {
            return false
        }
        requestCountInWindow++
        return true
    }

    private val chatSchema = Schema.obj(
        properties = mapOf(
            "reply" to Schema.string(description = "Helpful explanation or conversation response in natural Uzbek with English examples"),
            "suggestedRoute" to Schema.enumeration(
                values = listOf("NONE", "GRAMMAR", "VOCABULARY", "LISTENING", "SPEAKING", "READING", "WRITING", "PLACEMENT", "PROGRESS"),
                description = "Controlled in-app navigation route if the user asked to open a section, otherwise NONE"
            ),
            "suggestedLabel" to Schema.string(description = "Uzbek button label for the suggested route, or empty string")
        )
    )

    private val writingReviewSchema = Schema.obj(
        properties = mapOf(
            "correctedText" to Schema.string(description = "Corrected and natural English version of the user's writing"),
            "grammarScore" to Schema.integer(description = "Grammar score from 0 to 100"),
            "vocabularyScore" to Schema.integer(description = "Vocabulary score from 0 to 100"),
            "coherenceScore" to Schema.integer(description = "Organization and task relevance score from 0 to 100"),
            "overallScore" to Schema.integer(description = "Overall score from 0 to 100"),
            "explanationUzbek" to Schema.string(description = "Detailed, encouraging feedback in Uzbek explaining what was good and what needs improvement"),
            "keyMistakesUzbek" to Schema.array(
                items = Schema.string(),
                description = "List of specific grammar or vocabulary mistakes explained in Uzbek"
            ),
            "suggestionsUzbek" to Schema.array(
                items = Schema.string(),
                description = "Actionable suggestions in Uzbek to improve the writing"
            )
        )
    )

    private val speakingFeedbackSchema = Schema.obj(
        properties = mapOf(
            "correctedSentenceEn" to Schema.string(description = "Corrected and natural English sentence"),
            "grammarScore" to Schema.integer(description = "Grammar accuracy score 0..100 based on recognized transcript"),
            "vocabularyScore" to Schema.integer(description = "Vocabulary appropriateness score 0..100"),
            "clarityAndRelevanceScore" to Schema.integer(description = "Clarity and scenario relevance score 0..100"),
            "feedbackUzbek" to Schema.string(description = "Clear explanation in Uzbek of grammar, word choice, and relevance to the scenario"),
            "pronunciationTipUzbek" to Schema.string(description = "Helpful spoken English rhythm/stress tip in Uzbek for the key words in this sentence")
        )
    )

    private val extraQuizSchema = Schema.obj(
        properties = mapOf(
            "promptEn" to Schema.string(description = "English question prompt"),
            "promptUz" to Schema.string(description = "Uzbek instruction or translation hint"),
            "options" to Schema.array(items = Schema.string(), description = "4 multiple choice options in English"),
            "correctAnswer" to Schema.string(description = "Exact string matching one of the 4 options"),
            "explanationUz" to Schema.string(description = "Explanation in Uzbek why this answer is correct")
        )
    )

    private val tutorModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = chatSchema
                maxOutputTokens = 900
            }
        )
    }

    private val streamModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                maxOutputTokens = 800
            }
        )
    }

    private val writingModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = writingReviewSchema
                maxOutputTokens = 1200
            }
        )
    }

    private val speakingModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = speakingFeedbackSchema
                maxOutputTokens = 900
            }
        )
    }

    private val extraQuizModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = extraQuizSchema
                maxOutputTokens = 700
            }
        )
    }

    suspend fun askTutor(
        userMessage: String,
        context: TutorContext
    ): Result<TutorChatResponse> {
        val trimmed = userMessage.trim().take(1000)
        if (trimmed.isBlank()) {
            return Result.failure(IllegalArgumentException("Iltimos, savolingizni yozing."))
        }
        if (!checkRateLimit()) {
            return Result.failure(IllegalStateException("So'rovlar limiti: Daqiqasiga 15 tadan ko'p so'rov yuborildi. Biroz kutib qayta urinib ko'ring."))
        }

        val prompt = """
            Sen 'UstozBot' — o'zbek tilida so'zlashuvchi o'quvchilar uchun mehribon, aniq va professional ingliz tili AI ustozi (tutor) san.
            O'quvchining hozirgi darajasi: ${context.userLevel}.
            Hozirgi ekran: ${context.screenName}.
            Mavzu/Dars: ${context.lessonTitle.ifBlank { "Umumiy ingliz tili" }}.
            Joriy savol/mashq: ${context.currentQuestion.ifBlank { "Yo'q" }}.
            O'quvchining joriy javobi: ${context.userDraftAnswer.ifBlank { "Yo'q" }}.
            
            Qoidalar:
            1. Grammatika va xatolarni tabiiy, tushunarli O'ZBEK tilida tushuntir, misollarni esa INGLIZ tilida berib tarjima qil.
            2. Agar o'quvchi savolga yordam so'rasa, darhol tayyor javobni aytib qo'ymasdan, avval foydali ishora (hint) va qoidani tushuntir.
            3. Agar o'quvchi biror bo'limni ochishni so'rasa (masalan: "yozish mashqini och", "grammatikaga o't"), suggestedRoute maydoniga mos qiymatni (GRAMMAR, VOCABULARY, LISTENING, SPEAKING, READING, WRITING, PLACEMENT, PROGRESS) qo'y, aks holda NONE qoldir.
            
            O'quvchining xabari: "$trimmed"
        """.trimIndent()

        return try {
            val response = withTimeout(60_000L) {
                tutorModel.generateContent(prompt)
            }
            val jsonText = response.text ?: throw IllegalStateException("AI bo'sh javob qaytardi.")
            val json = JSONObject(jsonText)
            val reply = json.optString("reply", "Tushunarli! Keling, buni birga ko'rib chiqamiz.")
            val route = json.optString("suggestedRoute", "NONE")
            val label = json.optString("suggestedLabel", "")
            Result.success(
                TutorChatResponse(
                    replyUzbekAndEnglish = reply,
                    suggestedActionRoute = if (route != "NONE" && route.isNotBlank()) route else null,
                    suggestedActionLabel = label.ifBlank { null }
                )
            )
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Tutor request timed out", e)
            Result.failure(Exception("AI javobi kechikdi (60 soniya timeout). Internet aloqasini tekshirib qayta urinib ko'ring."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Tutor request failed", e)
            Result.failure(Exception(e.localizedMessage ?: "AI ustoz bilan bog'lanishda xatolik yuz berdi."))
        }
    }

    fun streamGrammarPersonalization(
        lessonTitle: String,
        cefrLevel: String,
        userQuestion: String
    ): Flow<String> = flow {
        val prompt = """
            O'zbek tilida so'zlashuvchi $cefrLevel darajadagi o'quvchi uchun "$lessonTitle" mavzusini juda sodda, hayotiy misollar bilan tushuntirib ber.
            O'quvchining qo'shimcha savoli: ${userQuestion.take(500)}
        """.trimIndent()
        val acc = StringBuilder()
        withTimeout(60_000L) {
            streamModel.generateContentStream(prompt).collect { chunk ->
                acc.append(chunk.text.orEmpty())
                emit(acc.toString())
            }
        }
    }

    suspend fun reviewWriting(
        promptTitle: String,
        promptInstructions: String,
        cefrLevel: String,
        userText: String
    ): Result<AiWritingReview> {
        val trimmed = userText.trim().take(2500)
        if (trimmed.length < 3) {
            return Result.failure(IllegalArgumentException("Iltimos, tekshirish uchun kamida bitta to'liq gap yozing."))
        }
        if (!checkRateLimit()) {
            return Result.failure(IllegalStateException("So'rovlar limiti tugadi. Biroz kutib qayta urinib ko'ring."))
        }

        val prompt = """
            Sen ingliz tili yozma ishlarini tekshiruvchi AI ustozsan. O'quvchining darajasi: $cefrLevel.
            Topshiriq nomi: $promptTitle
            Topshiriq sharti: $promptInstructions
            O'quvchining yozgan matni: "$trimmed"
            
            Matnni grammatika, lug'at boyligi, gap qurilishi va mavzuga mosligi bo'yicha bahola.
            Izohlarni tushunarli O'ZBEK tilida yoz. Asl matnni o'zgartirib yuborma, balki correctedText maydonida to'g'rilangan variantni alohida ko'rsat.
        """.trimIndent()

        return try {
            val response = withTimeout(60_000L) {
                writingModel.generateContent(prompt)
            }
            val jsonString = response.text ?: throw IllegalStateException("AI bo'sh javob qaytardi.")
            val json = JSONObject(jsonString)
            val mistakesArr = json.optJSONArray("keyMistakesUzbek")
            val mistakes = mutableListOf<String>()
            if (mistakesArr != null) {
                for (i in 0 until mistakesArr.length()) {
                    mistakes.add(mistakesArr.getString(i))
                }
            }
            val suggestionsArr = json.optJSONArray("suggestionsUzbek")
            val suggestions = mutableListOf<String>()
            if (suggestionsArr != null) {
                for (i in 0 until suggestionsArr.length()) {
                    suggestions.add(suggestionsArr.getString(i))
                }
            }

            Result.success(
                AiWritingReview(
                    originalText = trimmed,
                    correctedText = json.optString("correctedText", trimmed),
                    grammarScore = json.optInt("grammarScore", 75).coerceIn(0, 100),
                    vocabularyScore = json.optInt("vocabularyScore", 75).coerceIn(0, 100),
                    coherenceScore = json.optInt("coherenceScore", 80).coerceIn(0, 100),
                    overallScore = json.optInt("overallScore", 77).coerceIn(0, 100),
                    explanationUzbek = json.optString("explanationUzbek", "Yaxshi harakat! Xatolarni ko'rib chiqing."),
                    keyMistakesUzbek = mistakes,
                    suggestionsUzbek = suggestions
                )
            )
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Writing review timed out", e)
            Result.failure(Exception("Tekshirish vaqti tugadi (60s). Qayta urinib ko'ring."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Writing review failed", e)
            Result.failure(Exception(e.localizedMessage ?: "Yozma ishni tekshirishda xatolik yuz berdi."))
        }
    }

    suspend fun evaluateSpeakingTranscript(
        scenarioTitle: String,
        targetPromptEn: String,
        cefrLevel: String,
        recognizedText: String
    ): Result<AiSpeakingFeedback> {
        val trimmed = recognizedText.trim().take(1000)
        if (trimmed.isBlank()) {
            return Result.failure(IllegalArgumentException("Nutq matni bo'sh. Mikrofon tugmasini bosib gapiring yoki matn kiriting."))
        }
        if (!checkRateLimit()) {
            return Result.failure(IllegalStateException("So'rovlar limiti tugadi. Biroz kutib qayta urinib ko'ring."))
        }

        val prompt = """
            O'zbek tilida so'zlashuvchi o'quvchi ($cefrLevel) "$scenarioTitle" suhbat vaziyatida quyidagi savolga javob berdi.
            Savol/Vaziyat: "$targetPromptEn"
            Speech-to-text orqali aniqlangan matn (yoki kiritilgan javob): "$trimmed"
            
            Eslatma: Bu baholash matnga asoslangan (speech-to-text transcript), akustik fonetik tahlil emas.
            O'quvchining javobini grammatika, so'z tanlash, tushunarlilik va vaziyatga moslik bo'yicha bahola.
            Izohni O'ZBEK tilida yoz va aynan shu gapdagi asosiy so'zlarni to'g'ri talaffuz qilish bo'yicha foydali maslahat ber.
        """.trimIndent()

        return try {
            val response = withTimeout(60_000L) {
                speakingModel.generateContent(prompt)
            }
            val jsonString = response.text ?: throw IllegalStateException("AI bo'sh javob qaytardi.")
            val json = JSONObject(jsonString)
            Result.success(
                AiSpeakingFeedback(
                    recognizedText = trimmed,
                    correctedSentenceEn = json.optString("correctedSentenceEn", trimmed),
                    grammarScore = json.optInt("grammarScore", 80).coerceIn(0, 100),
                    vocabularyScore = json.optInt("vocabularyScore", 80).coerceIn(0, 100),
                    clarityAndRelevanceScore = json.optInt("clarityAndRelevanceScore", 85).coerceIn(0, 100),
                    feedbackUzbek = json.optString("feedbackUzbek", "Juda yaxshi urinish!"),
                    pronunciationTipUzbek = json.optString("pronunciationTipUzbek", "So'z urg'ulariga e'tibor bering.")
                )
            )
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Speaking feedback timed out", e)
            Result.failure(Exception("Tahlil vaqti tugadi (60s). Qayta urinib ko'ring."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Speaking feedback failed", e)
            Result.failure(Exception(e.localizedMessage ?: "Nutqni tahlil qilishda xatolik yuz berdi."))
        }
    }

    suspend fun generateExtraQuestion(
        lessonId: String,
        lessonTitle: String,
        cefrLevel: String
    ): Result<QuizQuestion> {
        if (!checkRateLimit()) {
            return Result.failure(IllegalStateException("So'rovlar limiti tugadi. Biroz kutib qayta urinib ko'ring."))
        }
        val prompt = """
            O'zbek tilida so'zlashuvchi $cefrLevel darajadagi o'quvchi uchun "$lessonTitle" mavzusida 1 ta yangi, amaliy multiple-choice (4 variantli) test savolini tuz.
            Savol ingliz tilida (promptEn), yordamchi ko'rsatma va tushuntirish o'zbek tilida (promptUz, explanationUz) bo'lsin.
            correctAnswer aynan options ro'yxatidagi 4 ta variantdan biriga 100% teng bo'lsin.
        """.trimIndent()

        return try {
            val response = withTimeout(60_000L) {
                extraQuizModel.generateContent(prompt)
            }
            val jsonString = response.text ?: throw IllegalStateException("AI bo'sh javob qaytardi.")
            val json = JSONObject(jsonString)
            val optionsArr = json.getJSONArray("options")
            val options = (0 until optionsArr.length()).map { optionsArr.getString(it) }
            val correct = json.getString("correctAnswer")
            val validCorrect = if (options.contains(correct)) correct else options.firstOrNull().orEmpty()

            Result.success(
                QuizQuestion(
                    id = "ai_q_${System.currentTimeMillis()}",
                    lessonId = lessonId,
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = json.getString("promptEn"),
                    promptUz = json.getString("promptUz"),
                    options = options,
                    correctAnswer = validCorrect,
                    explanationUz = json.getString("explanationUz"),
                    errorCategory = lessonTitle,
                    cefrLevel = cefrLevel
                )
            )
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Extra question timed out", e)
            Result.failure(Exception("Savol yaratish vaqti tugadi."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Extra question generation failed", e)
            Result.failure(Exception(e.localizedMessage ?: "Qo'shimcha mashq yaratishda xatolik."))
        }
    }
}
