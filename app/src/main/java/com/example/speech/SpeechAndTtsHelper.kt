package com.example.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class SpeechAndTtsHelper(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var speechRecognizer: SpeechRecognizer? = null

    init {
        try {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val res = tts?.setLanguage(Locale.US)
                    isTtsReady = res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED
                }
            }
        } catch (e: Exception) {
            Log.w("SpeechAndTtsHelper", "TTS initialization warning: ${e.message}")
        }
    }

    fun speakEnglish(text: String, slowRate: Boolean = false) {
        if (!isTtsReady) return
        try {
            tts?.setSpeechRate(if (slowRate) 0.72f else 1.0f)
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "uzenglish_utt_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.w("SpeechAndTtsHelper", "TTS speak error: ${e.message}")
        }
    }

    fun stopSpeaking() {
        try {
            tts?.stop()
        } catch (_: Exception) {
        }
    }

    fun isSpeechRecognitionAvailable(): Boolean {
        return try {
            SpeechRecognizer.isRecognitionAvailable(context)
        } catch (_: Exception) {
            false
        }
    }

    fun startListening(
        onPartialResult: (String) -> Unit,
        onFinalResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            stopListening()
            if (!isSpeechRecognitionAvailable()) {
                onError("Qurilmangizda ovozni matnga aylantirish (SpeechRecognizer) xizmati topilmadi. Matn kiritish maydonidan foydalaning.")
                return
            }
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {}
                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {}

                    override fun onError(error: Int) {
                        val msg = when (error) {
                            SpeechRecognizer.ERROR_AUDIO -> "Audio yozishda xatolik."
                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Mikrofon ruxsati berilmagan."
                            SpeechRecognizer.ERROR_NO_MATCH -> "Nutq aniqlanmadi. Iltimos, aniqroq gapirib qayta urinib ko'ring yoki matn kiriting."
                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Ovoz eshitilmadi. Qayta urinib ko'ring."
                            else -> "Nutqni aniqlashda xatolik (kod: $error). Matn orqali javob berishingiz mumkin."
                        }
                        onError(msg)
                    }

                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val text = matches?.firstOrNull().orEmpty()
                        if (text.isNotBlank()) {
                            onFinalResult(text)
                        } else {
                            onError("Ovozdan matn ajratib bo'lmadi.")
                        }
                    }

                    override fun onPartialResults(partialResults: Bundle?) {
                        val partial = partialResults
                            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            ?.firstOrNull()
                            .orEmpty()
                        if (partial.isNotBlank()) {
                            onPartialResult(partial)
                        }
                    }

                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            }
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            Log.e("SpeechAndTtsHelper", "startListening failed", e)
            onError(e.localizedMessage ?: "Mikrofonni ishga tushirib bo'lmadi.")
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
            speechRecognizer?.destroy()
            speechRecognizer = null
        } catch (_: Exception) {
        }
    }

    fun release() {
        stopSpeaking()
        stopListening()
        try {
            tts?.shutdown()
        } catch (_: Exception) {
        }
    }
}
