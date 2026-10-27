package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.GrammarAndSkillsCatalog
import com.example.data.VocabularyCatalog
import com.example.model.CefrLevel
import com.example.model.GrammarLesson
import com.example.model.LessonProgress
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.model.UiState
import com.example.model.UserProfile
import com.example.model.VocabProgress
import com.example.model.VocabWord
import com.example.ui.components.DeterministicQuizEngine
import com.example.ui.components.SilkRoadHeroBanner
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeDashboardScreen(
    profile: UserProfile,
    vocabProgress: List<VocabProgress>,
    lessonProgress: List<LessonProgress>,
    onNavigateToSection: (String) -> Unit,
    onOpenPlacementTest: () -> Unit,
    onOpenAiTutor: () -> Unit
) {
    val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
    val dueWordsCount = vocabProgress.count { it.learned && it.nextReviewEpochDay <= currentEpochDay }
    val learnedWordsCount = vocabProgress.count { it.learned }
    val completedLessonsCount = lessonProgress.count { it.completed }
    val goalProgress = (profile.todayMinutesDone.toFloat() / profile.dailyGoalMinutes.coerceAtLeast(5L).toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SilkRoadHeroBanner(
            title = "Salom, ${profile.displayName}!",
            subtitle = "Maqsad: ${profile.learningGoal}",
            cefrBadge = profile.cefrLevel
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Goal & Streak Card
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
                    Column {
                        Text(
                            text = "Kunlik o'quv maqsadi",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${profile.todayMinutesDone} / ${profile.dailyGoalMinutes} daqiqa bajarildi",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = WarningGold,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${profile.streakDays} kun",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { goalProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onNavigateToSection("GRAMMAR") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("continue_learning_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("O'qishni davom ettirish")
                    }
                    OutlinedButton(
                        onClick = onOpenPlacementTest,
                        modifier = Modifier.testTag("home_placement_test_button")
                    ) {
                        Text("Daraja testi")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Vocabulary Spaced Repetition Reminder Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToSection("VOCABULARY") },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Lug'at takrorlash eslatmasi (Spaced Repetition)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (dueWordsCount > 0) {
                            "Bugun takrorlash kerak bo'lgan $dueWordsCount ta so'z bor! ($learnedWordsCount ta o'rganilgan)"
                        } else {
                            "Yodlangan so'zlar: $learnedWordsCount / ${VocabularyCatalog.allWords.size}. Yangi so'zlarni o'rganing!"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { onNavigateToSection("VOCABULARY") }) {
                    Text("Mashq")
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "6 ta Ko'nikma Bo'limlari (Skills Shortcuts)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        val shortcuts = listOf(
            SkillShortcut("GRAMMAR", "Grammar", "A1–C2 Qoidalar", Icons.Default.School),
            SkillShortcut("VOCABULARY", "Vocabulary", "115+ So'z & Kartochka", Icons.Default.Spellcheck),
            SkillShortcut("LISTENING", "Listening", "Audio & Tushunish", Icons.Default.Headphones),
            SkillShortcut("SPEAKING", "Speaking", "Mikrofon & Suhbat", Icons.Default.Mic),
            SkillShortcut("READING", "Reading", "Hikoya & Tarjima", Icons.AutoMirrored.Filled.MenuBook),
            SkillShortcut("WRITING", "Writing", "Insho & AI Tahlil", Icons.Default.EditNote)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            shortcuts.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { item ->
                        ElevatedCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onNavigateToSection(item.route) }
                                .testTag("shortcut_${item.route.lowercase()}"),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Icon(
                                        item.icon,
                                        contentDescription = item.title,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = item.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Progress Overview & Recent Achievements
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
                    Text(
                        text = "Yutuqlar va Umumiy Ko'rsatkich",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = WarningGold)
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatBadge(value = "${profile.xpPoints}", label = "XP Ball")
                    StatBadge(value = "$completedLessonsCount", label = "Bitirilgan dars")
                    StatBadge(value = "$learnedWordsCount", label = "Yodlangan so'z")
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Savolingiz bormi? UstozBot doim yoningizda!",
                        style = MaterialTheme.typography.bodySmall
                    )
                    AssistChip(
                        onClick = onOpenAiTutor,
                        label = { Text("UstozBotni ochish") },
                        leadingIcon = {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(88.dp))
    }
}

private data class SkillShortcut(
    val route: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

@Composable
private fun StatBadge(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearnHubScreen(
    initialSubTab: String,
    userCefrLevel: String,
    vocabProgress: List<VocabProgress>,
    lessonProgress: List<LessonProgress>,
    extraQuestionsByLesson: Map<String, List<QuizQuestion>>,
    grammarAiExplanationState: UiState<String>,
    onSpeakEnglish: (String, Boolean) -> Unit,
    onToggleWordLearned: (VocabWord) -> Unit,
    onToggleWordFavorite: (VocabWord) -> Unit,
    onRecordSpacedRepetition: (VocabWord, Boolean) -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onRequestGrammarAiExplanation: (String, String) -> Unit,
    onGenerateExtraGrammarQuestion: (String, String) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    var selectedSubTab by remember(initialSubTab) {
        mutableStateOf(if (initialSubTab == "VOCABULARY") "VOCABULARY" else "GRAMMAR")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterChip(
                selected = selectedSubTab == "GRAMMAR",
                onClick = { selectedSubTab = "GRAMMAR" },
                label = { Text("Grammatika (A1–C2)") },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("subtab_grammar")
            )
            FilterChip(
                selected = selectedSubTab == "VOCABULARY",
                onClick = { selectedSubTab = "VOCABULARY" },
                label = { Text("Lug'at (Vocabulary)") },
                leadingIcon = { Icon(Icons.Default.Spellcheck, contentDescription = null, modifier = Modifier.size(18.dp)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("subtab_vocabulary")
            )
        }

        if (selectedSubTab == "GRAMMAR") {
            GrammarCurriculumSection(
                userCefrLevel = userCefrLevel,
                lessonProgress = lessonProgress,
                extraQuestionsByLesson = extraQuestionsByLesson,
                grammarAiExplanationState = grammarAiExplanationState,
                onSpeakEnglish = { onSpeakEnglish(it, false) },
                onRecordQuestionAttempt = onRecordQuestionAttempt,
                onCompleteLessonQuiz = onCompleteLessonQuiz,
                onRequestGrammarAiExplanation = onRequestGrammarAiExplanation,
                onGenerateExtraGrammarQuestion = onGenerateExtraGrammarQuestion,
                onUpdateTutorContext = onUpdateTutorContext,
                onOpenAiTutor = onOpenAiTutor
            )
        } else {
            VocabularyModuleSection(
                vocabProgress = vocabProgress,
                onSpeakEnglish = onSpeakEnglish,
                onToggleWordLearned = onToggleWordLearned,
                onToggleWordFavorite = onToggleWordFavorite,
                onRecordSpacedRepetition = onRecordSpacedRepetition,
                onRecordQuestionAttempt = onRecordQuestionAttempt,
                onCompleteLessonQuiz = onCompleteLessonQuiz,
                onUpdateTutorContext = onUpdateTutorContext,
                onOpenAiTutor = onOpenAiTutor
            )
        }
    }
}

@Composable
fun GrammarCurriculumSection(
    userCefrLevel: String,
    lessonProgress: List<LessonProgress>,
    extraQuestionsByLesson: Map<String, List<QuizQuestion>>,
    grammarAiExplanationState: UiState<String>,
    onSpeakEnglish: (String) -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onRequestGrammarAiExplanation: (String, String) -> Unit,
    onGenerateExtraGrammarQuestion: (String, String) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    var selectedLevelFilter by remember { mutableStateOf("Barchasi") }
    var activeLesson by remember { mutableStateOf<GrammarLesson?>(null) }
    var customAiQuestion by remember { mutableStateOf("") }

    val levelFilters = listOf("Barchasi", "A1", "A2", "B1", "B2", "C1", "C2")

    if (activeLesson != null) {
        val lesson = activeLesson!!
        val combinedQuestions = lesson.questions + extraQuestionsByLesson[lesson.id].orEmpty()
        onUpdateTutorContext("Grammatika darsi", lesson.titleEn, "")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { activeLesson = null }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Orqaga")
                }
                Column {
                    Text(
                        text = "${lesson.cefrLevel} • ${lesson.titleEn}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = lesson.titleUz,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 1. Uzbek Explanation & Formula
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. O'zbek tilida tushuntirish",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = lesson.explanationUz, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Formula: ${lesson.formula}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("✅ Darak (+): ${lesson.affirmativeForm}", style = MaterialTheme.typography.bodySmall)
                            Text("❌ Inkor (-): ${lesson.negativeForm}", style = MaterialTheme.typography.bodySmall)
                            Text("❓ So'roq (?): ${lesson.questionForm}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Bilingual Examples with Audio
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. Misollar (Inglizcha va O'zbekcha)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    lesson.examples.forEach { ex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onSpeakEnglish(ex.english) }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Eshitish",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Column {
                                Text(text = ex.english, fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = ex.uzbek,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Common Mistakes
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.45f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. Ko'p uchraydigan xatolar (Common Mistakes)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    lesson.commonMistakes.forEach { m ->
                        Text(text = "❌ Xato: ${m.wrongEn}", fontWeight = FontWeight.SemiBold)
                        Text(text = "✅ To'g'ri: ${m.correctEn}", color = SuccessGreen, fontWeight = FontWeight.Bold)
                        Text(text = "Izoh: ${m.reasonUz}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(
                        text = "💡 Maslahat: ${lesson.guidedPracticeHintUz}",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. AI Personalized Explanation & Extra Exercise Generator
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "4. AI Ustoz yordamida chuqurroq o'rganish",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customAiQuestion,
                        onValueChange = { customAiQuestion = it },
                        label = { Text("Shu mavzu bo'yicha savolingiz (ixtiyoriy)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onRequestGrammarAiExplanation(
                                    lesson.titleEn,
                                    customAiQuestion.ifBlank { "Menga yanada soddaroq misollar bilan tushuntirib ber" }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("AI Tushuntirish")
                        }
                        OutlinedButton(
                            onClick = { onGenerateExtraGrammarQuestion(lesson.id, lesson.titleEn) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+ Yangi AI Test")
                        }
                    }

                    when (grammarAiExplanationState) {
                        is UiState.Loading -> {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("UstozBot shaxsiy tushuntirish yozmoqda...")
                            }
                        }
                        is UiState.Success -> {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = grammarAiExplanationState.data,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                        is UiState.Error -> {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = grammarAiExplanationState.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        UiState.Idle -> {}
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. Interactive Quiz
            Text(
                text = "5. Mavzu bo'yicha Amaliy Test (${combinedQuestions.size} savol)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            DeterministicQuizEngine(
                questions = combinedQuestions,
                skillType = "GRAMMAR",
                onQuestionAnswered = { q, ans, ok ->
                    onRecordQuestionAttempt(q, "GRAMMAR", ans, ok)
                },
                onQuizCompleted = { scorePct ->
                    onCompleteLessonQuiz(lesson.id, "GRAMMAR", lesson.titleEn, scorePct)
                },
                onAskAiHelp = { q, draft ->
                    onUpdateTutorContext("Grammatika testi", lesson.titleEn, q.promptEn + " (Javobim: $draft)")
                    onOpenAiTutor()
                },
                onSpeakEnglish = onSpeakEnglish
            )

            Spacer(modifier = Modifier.height(88.dp))
        }
        return
    }

    val filteredLessons = remember(selectedLevelFilter) {
        if (selectedLevelFilter == "Barchasi") {
            GrammarAndSkillsCatalog.grammarLessons
        } else {
            GrammarAndSkillsCatalog.grammarLessons.filter { it.cefrLevel == selectedLevelFilter }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(levelFilters) { lvl ->
                    FilterChip(
                        selected = selectedLevelFilter == lvl,
                        onClick = { selectedLevelFilter = lvl },
                        label = { Text(lvl) }
                    )
                }
            }
        }

        items(filteredLessons, key = { it.id }) { lesson ->
            val progress = lessonProgress.find { it.lessonId == lesson.id }
            val isCompleted = progress?.completed == true

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { activeLesson = lesson }
                    .testTag("grammar_lesson_${lesson.id}"),
                shape = RoundedCornerShape(18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = if (lesson.cefrLevel == userCefrLevel) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.secondaryContainer
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = lesson.cefrLevel,
                                    color = if (lesson.cefrLevel == userCefrLevel) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = lesson.titleEn,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lesson.titleUz,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (progress != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Eng yuqori natija: ${progress.bestScore}% • Urinishlar: ${progress.attemptsCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = SuccessGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(92.dp)) }
    }
}

@Composable
fun VocabularyModuleSection(
    vocabProgress: List<VocabProgress>,
    onSpeakEnglish: (String, Boolean) -> Unit,
    onToggleWordLearned: (VocabWord) -> Unit,
    onToggleWordFavorite: (VocabWord) -> Unit,
    onRecordSpacedRepetition: (VocabWord, Boolean) -> Unit,
    onRecordQuestionAttempt: (QuizQuestion, String, String, Boolean) -> Unit,
    onCompleteLessonQuiz: (String, String, String, Int) -> Unit,
    onUpdateTutorContext: (String, String, String) -> Unit,
    onOpenAiTutor: () -> Unit
) {
    var mode by remember { mutableStateOf("LIST") } // LIST, FLASHCARDS, SRS, QUIZ
    var selectedTopic by remember { mutableStateOf("Barchasi") }
    var searchQuery by remember { mutableStateOf("") }
    var onlyFavorites by remember { mutableStateOf(false) }

    val progressMap = remember(vocabProgress) { vocabProgress.associateBy { it.wordId } }

    val filteredWords = remember(selectedTopic, searchQuery, onlyFavorites, progressMap) {
        VocabularyCatalog.allWords.filter { word ->
            val matchesTopic = selectedTopic == "Barchasi" || word.topic == selectedTopic
            val matchesQuery = searchQuery.isBlank() ||
                word.english.contains(searchQuery, ignoreCase = true) ||
                word.uzbek.contains(searchQuery, ignoreCase = true)
            val matchesFav = !onlyFavorites || (progressMap[word.id]?.favorite == true)
            matchesTopic && matchesQuery && matchesFav
        }
    }

    onUpdateTutorContext("Lug'at ($selectedTopic)", "Vocabulary (${filteredWords.size} so'z)", "")

    Column(modifier = Modifier.fillMaxSize()) {
        // Mode Selector Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = mode == "LIST",
                    onClick = { mode = "LIST" },
                    label = { Text("Ro'yxat (${filteredWords.size})") }
                )
            }
            item {
                FilterChip(
                    selected = mode == "FLASHCARDS",
                    onClick = { mode = "FLASHCARDS" },
                    label = { Text("Kartochkalar (Flashcards)") },
                    leadingIcon = { Icon(Icons.Default.Flip, contentDescription = null, modifier = Modifier.size(16.dp)) }
                )
            }
            item {
                FilterChip(
                    selected = mode == "SRS",
                    onClick = { mode = "SRS" },
                    label = { Text("Spaced Repetition") }
                )
            }
            item {
                FilterChip(
                    selected = mode == "QUIZ",
                    onClick = { mode = "QUIZ" },
                    label = { Text("Test & Yozish mashqi") },
                    leadingIcon = { Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp)) }
                )
            }
        }

        // Topic Filter Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(VocabularyCatalog.topics) { topic ->
                FilterChip(
                    selected = selectedTopic == topic,
                    onClick = { selectedTopic = topic },
                    label = { Text(topic) }
                )
            }
        }

        when (mode) {
            "LIST" -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Inglizcha yoki o'zbekcha qidirish...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = onlyFavorites,
                        onClick = { onlyFavorites = !onlyFavorites },
                        label = { Text("Tanlangan") },
                        leadingIcon = {
                            Icon(
                                if (onlyFavorites) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredWords, key = { it.id }) { word ->
                        val prog = progressMap[word.id]
                        val isLearned = prog?.learned == true
                        val isFav = prog?.favorite == true

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = word.english,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = word.pronunciation,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    text = word.cefrLevel,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = word.uzbek,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    Row {
                                        IconButton(onClick = { onSpeakEnglish(word.english + ". " + word.exampleEn, false) }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.VolumeUp,
                                                contentDescription = "Talaffuzni eshitish",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        IconButton(onClick = { onSpeakEnglish(word.english, true) }) {
                                            Icon(
                                                Icons.Default.SlowMotionVideo,
                                                contentDescription = "Sekin eshitish",
                                                tint = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                        IconButton(onClick = { onToggleWordFavorite(word) }) {
                                            Icon(
                                                if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "Sevimlilarga qo'shish",
                                                tint = if (isFav) WarningGold else MaterialTheme.colorScheme.outline
                                            )
                                        }
                                        IconButton(onClick = { onToggleWordLearned(word) }) {
                                            Icon(
                                                if (isLearned) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                                contentDescription = "O'rganildi deb belgilash",
                                                tint = if (isLearned) SuccessGreen else MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Misol: \"${word.exampleEn}\"",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Tarjima: \"${word.exampleUz}\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(92.dp)) }
                }
            }

            "FLASHCARDS" -> {
                val deck = filteredWords.ifEmpty { VocabularyCatalog.allWords }
                var cardIdx by remember(deck) { mutableIntStateOf(0) }
                var isFlipped by remember(cardIdx) { mutableStateOf(false) }
                val currentWord = deck[cardIdx.coerceIn(0, deck.lastIndex)]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kartochka ${cardIdx + 1} / ${deck.size} (Kartochka ustiga bosib aylantiring)",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clickable { isFlipped = !isFlipped }
                            .testTag("vocab_flashcard"),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (!isFlipped) {
                                Text(
                                    text = currentWord.english,
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentWord.pronunciation,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                OutlinedButton(onClick = { onSpeakEnglish(currentWord.english, false) }) {
                                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Eshitish")
                                }
                            } else {
                                Text(
                                    text = currentWord.uzbek,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = currentWord.exampleEn,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = currentWord.exampleUz,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { if (cardIdx > 0) cardIdx-- },
                            enabled = cardIdx > 0,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Oldingi")
                        }
                        Button(
                            onClick = {
                                onToggleWordLearned(currentWord)
                                if (cardIdx + 1 < deck.size) cardIdx++
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Yodladim ✅")
                        }
                        OutlinedButton(
                            onClick = { if (cardIdx + 1 < deck.size) cardIdx++ else cardIdx = 0 },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Keyingi")
                        }
                    }
                }
            }

            "SRS" -> {
                val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
                val dueWords = remember(vocabProgress, filteredWords) {
                    val dueIds = vocabProgress
                        .filter { it.nextReviewEpochDay <= currentEpochDay }
                        .map { it.wordId }
                        .toSet()
                    val list = VocabularyCatalog.allWords.filter { it.id in dueIds }
                    list.ifEmpty { filteredWords.take(15) }
                }
                var srsIdx by remember(dueWords) { mutableIntStateOf(0) }
                var showTranslation by remember(srsIdx) { mutableStateOf(false) }
                val word = dueWords[srsIdx.coerceIn(0, dueWords.lastIndex)]
                val boxNum = progressMap[word.id]?.masteryBox ?: 1L

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Leitner Spaced Repetition • Quti #$boxNum (${srsIdx + 1}/${dueWords.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = word.english,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = word.pronunciation, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(10.dp))
                            IconButton(onClick = { onSpeakEnglish(word.english, false) }) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Eshitish")
                            }

                            if (!showTranslation) {
                                Button(onClick = { showTranslation = true }) {
                                    Text("Tarjimani ko'rish")
                                }
                            } else {
                                Text(
                                    text = word.uzbek,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = word.exampleEn)
                                Text(text = word.exampleUz, style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            onRecordSpacedRepetition(word, false)
                                            if (srsIdx + 1 < dueWords.size) srsIdx++ else srsIdx = 0
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Qiyin bo'ldi (1 kunda)")
                                    }
                                    Button(
                                        onClick = {
                                            onRecordSpacedRepetition(word, true)
                                            if (srsIdx + 1 < dueWords.size) srsIdx++ else srsIdx = 0
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Esladim! (+Quti)")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "QUIZ" -> {
                val quizWords = remember(filteredWords) {
                    (filteredWords.ifEmpty { VocabularyCatalog.allWords }).take(8)
                }
                val generatedQuestions = remember(quizWords) {
                    quizWords.mapIndexed { idx, w ->
                        if (idx % 2 == 0) {
                            val distractors = VocabularyCatalog.allWords
                                .filter { it.id != w.id }
                                .shuffled()
                                .take(3)
                                .map { it.uzbek }
                            val opts = (distractors + w.uzbek).shuffled()
                            QuizQuestion(
                                id = "vq_${w.id}",
                                lessonId = "vocab_quiz",
                                type = QuestionType.MULTIPLE_CHOICE,
                                promptEn = "What is the Uzbek translation of '${w.english}'?",
                                promptUz = "Misol: \"${w.exampleEn}\"",
                                options = opts,
                                correctAnswer = w.uzbek,
                                explanationUz = "'${w.english}' so'zining ma'nosi — '${w.uzbek}'.",
                                errorCategory = "Vocabulary (${w.topic})",
                                cefrLevel = w.cefrLevel
                            )
                        } else {
                            QuizQuestion(
                                id = "vt_${w.id}",
                                lessonId = "vocab_quiz",
                                type = QuestionType.TYPED_ANSWER,
                                promptEn = "Type the English word for: '${w.uzbek}'",
                                promptUz = "Talaffuzi: ${w.pronunciation} • Misol: ${w.exampleUz}",
                                options = emptyList(),
                                correctAnswer = w.english,
                                explanationUz = "'${w.uzbek}' ingliz tilida '${w.english}' deb yoziladi.",
                                errorCategory = "Vocabulary Spelling",
                                cefrLevel = w.cefrLevel
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    DeterministicQuizEngine(
                        questions = generatedQuestions,
                        skillType = "VOCABULARY",
                        onQuestionAnswered = { q, ans, ok ->
                            onRecordQuestionAttempt(q, "VOCABULARY", ans, ok)
                        },
                        onQuizCompleted = { pct ->
                            onCompleteLessonQuiz("vocab_quiz_${selectedTopic}", "VOCABULARY", "Lug'at testi: $selectedTopic", pct)
                        },
                        onAskAiHelp = { q, draft ->
                            onUpdateTutorContext("Lug'at testi", selectedTopic, q.promptEn + " ($draft)")
                            onOpenAiTutor()
                        },
                        onSpeakEnglish = { onSpeakEnglish(it, false) }
                    )
                    Spacer(modifier = Modifier.height(88.dp))
                }
            }
        }
    }
}
