package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.GrammarAndSkillsCatalog
import com.example.model.AiSpeakingFeedback
import com.example.model.AiWritingReview
import com.example.model.QuizQuestion
import com.example.model.UiState
import com.example.model.WordMeaning
import com.example.speech.SpeechAndTtsHelper
import com.example.ui.components.DeterministicQuizEngine
import com.example.ui.theme.SuccessGreen

@Composable
fun PracticeHubScreen(
    initialTab: String,
    speechHelper: SpeechAndTtsHelper,
    defaultShowUzbek: Boolean,
    writingReviewState: UiState<AiWritingReview>,
    speakingFeedbackState: UiState<AiSpeakingFeedback>,
    onSpeakEnglish: (String, Boolean) -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onSubmitWritingForAi: (String, String, String, String) -> Unit,
    onResetWritingReview: () -> Unit,
    onSubmitSpeakingForAi: (String, String, String, String) -> Unit,
    onResetSpeakingFeedback: () -> Unit,
    onSaveWordFromGlossary: (String, String, String) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    var selectedSkill by remember(initialTab) {
        mutableStateOf(
            when (initialTab) {
                "SPEAKING", "READING", "WRITING" -> initialTab
                else -> "LISTENING"
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val tabs = listOf(
                Triple("LISTENING", "Listening", Icons.Default.Headphones),
                Triple("SPEAKING", "Speaking", Icons.Default.Mic),
                Triple("READING", "Reading", Icons.AutoMirrored.Filled.MenuBook),
                Triple("WRITING", "Writing", Icons.Default.EditNote)
            )
            tabs.forEach { (key, title, icon) ->
                FilterChip(
                    selected = selectedSkill == key,
                    onClick = { selectedSkill = key },
                    label = { Text(title, style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("practice_tab_${key.lowercase()}")
                )
            }
        }

        when (selectedSkill) {
            "LISTENING" -> ListeningPracticeSection(
                onSpeakEnglish = onSpeakEnglish,
                onStopAudio = { speechHelper.stopSpeaking() },
                onRecordQuestionAttempt = onRecordQuestionAttempt,
                onCompleteLessonQuiz = onCompleteLessonQuiz,
                onUpdateTutorContext = onUpdateTutorContext,
                onOpenAiTutor = onOpenAiTutor
            )
            "SPEAKING" -> SpeakingPracticeSection(
                speechHelper = speechHelper,
                speakingFeedbackState = speakingFeedbackState,
                onSpeakEnglish = { onSpeakEnglish(it, false) },
                onSubmitSpeakingForAi = onSubmitSpeakingForAi,
                onResetSpeakingFeedback = onResetSpeakingFeedback,
                onUpdateTutorContext = onUpdateTutorContext
            )
            "READING" -> ReadingPracticeSection(
                defaultShowUzbek = defaultShowUzbek,
                onSpeakEnglish = { onSpeakEnglish(it, false) },
                onSaveWordFromGlossary = onSaveWordFromGlossary,
                onRecordQuestionAttempt = onRecordQuestionAttempt,
                onCompleteLessonQuiz = onCompleteLessonQuiz,
                onUpdateTutorContext = onUpdateTutorContext,
                onOpenAiTutor = onOpenAiTutor
            )
            "WRITING" -> WritingPracticeSection(
                writingReviewState = writingReviewState,
                onSubmitWritingForAi = onSubmitWritingForAi,
                onResetWritingReview = onResetWritingReview,
                onUpdateTutorContext = onUpdateTutorContext
            )
        }
    }
}

@Composable
private fun ListeningPracticeSection(
    onSpeakEnglish: (String, Boolean) -> Unit,
    onStopAudio: () -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    val exercises = GrammarAndSkillsCatalog.listeningExercises
    var selectedIdx by remember { mutableIntStateOf(0) }
    var showTranscript by remember(selectedIdx) { mutableStateOf(false) }
    val exercise = exercises[selectedIdx]

    onUpdateTutorContext("Tinglab tushunish (Listening)", exercise.titleEn, "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            exercises.forEachIndexed { idx, ex ->
                FilterChip(
                    selected = selectedIdx == idx,
                    onClick = { selectedIdx = idx },
                    label = { Text("${ex.cefrLevel}: #${idx + 1}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${exercise.cefrLevel} • ${exercise.titleEn}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = exercise.titleUz,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onSpeakEnglish(exercise.transcriptEn, false) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("listening_play_normal_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Tinglash (1.0x)")
                    }
                    OutlinedButton(
                        onClick = { onSpeakEnglish(exercise.transcriptEn, true) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("listening_play_slow_button")
                    ) {
                        Icon(Icons.Default.SlowMotionVideo, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sekin (0.72x)")
                    }
                    OutlinedButton(onClick = onStopAudio) {
                        Icon(Icons.Default.Stop, contentDescription = "To'xtatish")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { showTranscript = !showTranscript },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (showTranscript) "Matnni yashirish" else "Audio matni va o'zbekcha xulosani ko'rish")
                }

                if (showTranscript) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = exercise.transcriptEn,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "O'zbekcha mazmuni: ${exercise.summaryUz}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DeterministicQuizEngine(
            questions = exercise.questions,
            skillType = "LISTENING",
            onQuestionAnswered = { q, ans, ok ->
                onRecordQuestionAttempt(q, "LISTENING", ans, ok)
            },
            onQuizCompleted = { pct ->
                onCompleteLessonQuiz(exercise.id, "LISTENING", exercise.titleEn, pct)
            },
            onAskAiHelp = { q, draft ->
                onUpdateTutorContext("Listening savoli", exercise.titleEn, q.promptEn + " ($draft)")
                onOpenAiTutor()
            },
            onSpeakEnglish = { onSpeakEnglish(it, false) }
        )

        Spacer(modifier = Modifier.height(88.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SpeakingPracticeSection(
    speechHelper: SpeechAndTtsHelper,
    speakingFeedbackState: UiState<AiSpeakingFeedback>,
    onSpeakEnglish: (String) -> Unit,
    onSubmitSpeakingForAi: (String, String, String, String) -> Unit,
    onResetSpeakingFeedback: () -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    val scenarios = GrammarAndSkillsCatalog.speakingScenarios
    var selectedIdx by remember { mutableIntStateOf(0) }
    val scenario = scenarios[selectedIdx]

    var recognizedOrTypedText by remember(selectedIdx) { mutableStateOf("") }
    var previousAttemptText by remember(selectedIdx) { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var micStatusMessage by remember { mutableStateOf<String?>(null) }

    onUpdateTutorContext("So'zlashish (Speaking)", scenario.titleEn, scenario.samplePromptEn)

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            isRecording = true
            micStatusMessage = "Gapiring (Ingliz tilida)..."
            speechHelper.startListening(
                onPartialResult = { recognizedOrTypedText = it },
                onFinalResult = {
                    recognizedOrTypedText = it
                    isRecording = false
                    micStatusMessage = "Nutq matnga aylantirildi!"
                },
                onError = { err ->
                    isRecording = false
                    micStatusMessage = err
                }
            )
        } else {
            micStatusMessage = "Mikrofon ruxsati berilmadi. Quyidagi matn maydoniga yozib mashq qilishingiz mumkin."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            scenarios.forEachIndexed { idx, sc ->
                FilterChip(
                    selected = selectedIdx == idx,
                    onClick = {
                        selectedIdx = idx
                        onResetSpeakingFeedback()
                    },
                    label = { Text("${sc.cefrLevel}: ${sc.titleUz}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${scenario.cefrLevel} • ${scenario.titleEn}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = scenario.situationUz, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Suhbatdosh savoli:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedButton(onClick = { onSpeakEnglish(scenario.samplePromptEn) }) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Eshitish")
                            }
                        }
                        Text(text = "\"${scenario.samplePromptEn}\"", fontWeight = FontWeight.SemiBold)
                        Text(
                            text = scenario.samplePromptUz,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Foydali iboralar (bosib talaffuzini eshiting):",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    scenario.targetPhrasesEn.forEach { phrase ->
                        AssistChip(
                            onClick = { onSpeakEnglish(phrase) },
                            label = { Text(phrase) },
                            leadingIcon = {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Microphone Controls + Clear Disclaimer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (isRecording) {
                                speechHelper.stopListening()
                                isRecording = false
                                micStatusMessage = "To'xtatildi."
                            } else {
                                val hasPerm = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED
                                if (hasPerm) {
                                    isRecording = true
                                    micStatusMessage = "Gapiring (Ingliz tilida)..."
                                    speechHelper.startListening(
                                        onPartialResult = { recognizedOrTypedText = it },
                                        onFinalResult = {
                                            recognizedOrTypedText = it
                                            isRecording = false
                                            micStatusMessage = "Nutq matnga aylantirildi!"
                                        },
                                        onError = { err ->
                                            isRecording = false
                                            micStatusMessage = err
                                        }
                                    )
                                } else {
                                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("speaking_mic_button")
                    ) {
                        Icon(if (isRecording) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isRecording) "Yozishni to'xtatish" else "Mikrofon orqali gapirish")
                    }
                }

                if (micStatusMessage != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = micStatusMessage ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = recognizedOrTypedText,
                    onValueChange = { recognizedOrTypedText = it },
                    label = { Text("Aniqlangan nutq matni (yoki qo'lda yozish fallback)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("speaking_text_input"),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Eslatma: Ilova Android Speech-to-Text orqali nutqingizni matnga aylantirib, grammatika, lug'at va mantiqiy aniqlikni AI yordamida tahlil qiladi (bu akustik fonetik baholash emas).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            onSubmitSpeakingForAi(
                                scenario.id,
                                scenario.titleEn,
                                scenario.samplePromptEn,
                                recognizedOrTypedText
                            )
                        },
                        enabled = recognizedOrTypedText.isNotBlank() && speakingFeedbackState !is UiState.Loading,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("evaluate_speaking_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI Tahlil va Tekshirish")
                    }
                    OutlinedButton(
                        onClick = {
                            previousAttemptText = recognizedOrTypedText
                            recognizedOrTypedText = ""
                            onResetSpeakingFeedback()
                        }
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Qayta")
                    }
                }
            }
        }

        if (previousAttemptText.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Oldingi urinishingiz bilan solishtirish:", fontWeight = FontWeight.Bold)
                    Text("Oldingi: \"$previousAttemptText\"", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        when (speakingFeedbackState) {
            is UiState.Loading -> {
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("AI nutqingizni tahlil qilmoqda...")
                }
            }
            is UiState.Success -> {
                val fb = speakingFeedbackState.data
                Spacer(modifier = Modifier.height(14.dp))
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "AI Nutq Tahlili Natijasi",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Sizning javobingiz: \"${fb.recognizedText}\"")
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Tavsiya etilgan tabiiy variant: \"${fb.correctedSentenceEn}\"",
                                color = SuccessGreen,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedButton(onClick = { onSpeakEnglish(fb.correctedSentenceEn) }) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Grammatika: ${fb.grammarScore}% • Lug'at: ${fb.vocabularyScore}% • Aniqlik: ${fb.clarityAndRelevanceScore}%")
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(text = fb.feedbackUzbek, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "🗣️ Talaffuz maslahati: ${fb.pronunciationTipUzbek}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            is UiState.Error -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(speakingFeedbackState.message, color = MaterialTheme.colorScheme.error)
            }
            UiState.Idle -> {}
        }

        Spacer(modifier = Modifier.height(88.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ReadingPracticeSection(
    defaultShowUzbek: Boolean,
    onSpeakEnglish: (String) -> Unit,
    onSaveWordFromGlossary: (String, String, String) -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    val passages = GrammarAndSkillsCatalog.readingPassages
    var selectedIdx by remember { mutableIntStateOf(0) }
    var showUzbekTranslation by remember(defaultShowUzbek) { mutableStateOf(defaultShowUzbek) }
    var selectedGlossaryWord by remember(selectedIdx) { mutableStateOf<WordMeaning?>(null) }
    val passage = passages[selectedIdx]

    onUpdateTutorContext("O'qib tushunish (Reading)", passage.titleEn, "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            passages.forEachIndexed { idx, p ->
                FilterChip(
                    selected = selectedIdx == idx,
                    onClick = { selectedIdx = idx },
                    label = { Text("${p.cefrLevel}: ${p.titleEn}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${passage.cefrLevel} • ${passage.titleEn}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = passage.titleUz,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    OutlinedButton(onClick = { onSpeakEnglish(passage.passageEn) }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = passage.passageEn,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = { showUzbekTranslation = !showUzbekTranslation },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (showUzbekTranslation) "O'zbekcha tarjimani yashirish" else "O'zbekcha tarjimani ko'rsatish")
                }

                if (showUzbekTranslation) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = passage.translationUz,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Muhim so'zlar (ustiga bosib ma'nosini ko'ring va lug'atga qo'shing):",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    passage.glossary.forEach { wm ->
                        AssistChip(
                            onClick = { selectedGlossaryWord = wm },
                            label = { Text(wm.word) }
                        )
                    }
                }

                if (selectedGlossaryWord != null) {
                    val gw = selectedGlossaryWord!!
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${gw.word} ${gw.pronunciation}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Ma'nosi: ${gw.meaningUz}")
                            }
                            Button(
                                onClick = {
                                    onSaveWordFromGlossary(gw.word, gw.meaningUz, gw.pronunciation)
                                }
                            ) {
                                Icon(Icons.Default.AddCircleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Saqlash")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DeterministicQuizEngine(
            questions = passage.questions,
            skillType = "READING",
            onQuestionAnswered = { q, ans, ok ->
                onRecordQuestionAttempt(q, "READING", ans, ok)
            },
            onQuizCompleted = { pct ->
                onCompleteLessonQuiz(passage.id, "READING", passage.titleEn, pct)
            },
            onAskAiHelp = { q, draft ->
                onUpdateTutorContext("Reading savoli", passage.titleEn, q.promptEn + " ($draft)")
                onOpenAiTutor()
            },
            onSpeakEnglish = onSpeakEnglish
        )

        Spacer(modifier = Modifier.height(88.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WritingPracticeSection(
    writingReviewState: UiState<AiWritingReview>,
    onSubmitWritingForAi: (String, String, String, String) -> Unit,
    onResetWritingReview: () -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit
) {
    val prompts = GrammarAndSkillsCatalog.writingPrompts
    var selectedIdx by remember { mutableIntStateOf(0) }
    val prompt = prompts[selectedIdx]
    var userEssayText by remember(selectedIdx) { mutableStateOf("") }

    onUpdateTutorContext("Yozish mashqi (Writing)", prompt.titleUz, userEssayText)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            prompts.forEachIndexed { idx, p ->
                FilterChip(
                    selected = selectedIdx == idx,
                    onClick = {
                        selectedIdx = idx
                        onResetWritingReview()
                    },
                    label = { Text("${p.cefrLevel}: ${p.titleUz}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${prompt.cefrLevel} • ${prompt.category}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = prompt.titleUz,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = prompt.promptEn, fontWeight = FontWeight.SemiBold)
                Text(
                    text = prompt.instructionsUz,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Tavsiya etilgan tayanch so'zlar:",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    prompt.targetKeywords.forEach { kw ->
                        val used = userEssayText.contains(kw, ignoreCase = true)
                        FilterChip(
                            selected = used,
                            onClick = {
                                userEssayText = if (userEssayText.isBlank()) kw else "$userEssayText $kw"
                            },
                            label = { Text(kw) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = userEssayText,
                    onValueChange = { userEssayText = it },
                    label = { Text("Javobingizni ingliz tilida yozing...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("writing_input_field"),
                    minLines = 5
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            onSubmitWritingForAi(
                                prompt.id,
                                prompt.titleUz,
                                prompt.promptEn,
                                userEssayText
                            )
                        },
                        enabled = userEssayText.trim().length >= 3 && writingReviewState !is UiState.Loading,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("submit_writing_ai_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI Ustoz tekshiruvi")
                    }
                    OutlinedButton(
                        onClick = { userEssayText = prompt.sampleAnswerEn }
                    ) {
                        Text("Namuna")
                    }
                }
            }
        }

        when (writingReviewState) {
            is UiState.Loading -> {
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("AI yozma ishingizni rubrika asosida tekshirmoqda...")
                }
            }
            is UiState.Success -> {
                val rev = writingReviewState.data
                Spacer(modifier = Modifier.height(14.dp))
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "AI Baholash Natijasi: ${rev.overallScore} / 100",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Grammatika: ${rev.grammarScore}% • Lug'at: ${rev.vocabularyScore}% • Mantiq va Tuzilish: ${rev.coherenceScore}%",
                            style = MaterialTheme.typography.labelLarge
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                        Text(
                            text = "1. Sizning asl javobingiz (o'zgartirilmagan):",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(text = rev.originalText, modifier = Modifier.padding(10.dp))
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "2. To'g'rilangan va mukammallashtirilgan variant:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                        Surface(
                            color = SuccessGreen.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = rev.correctedText,
                                modifier = Modifier.padding(10.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "3. O'zbek tilida umumiy tahlil:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = rev.explanationUzbek, style = MaterialTheme.typography.bodyMedium)

                        if (rev.keyMistakesUzbek.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Muhim xatolar tahlili:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            rev.keyMistakesUzbek.forEach { m ->
                                Text("• $m", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        if (rev.suggestionsUzbek.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tavsiyalar:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            rev.suggestionsUzbek.forEach { s ->
                                Text("💡 $s", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(writingReviewState.message, color = MaterialTheme.colorScheme.error)
            }
            UiState.Idle -> {}
        }

        Spacer(modifier = Modifier.height(88.dp))
    }
}
