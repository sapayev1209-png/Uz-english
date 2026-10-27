package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.GrammarAndSkillsCatalog
import com.example.data.VocabularyCatalog
import com.example.model.CefrLevel
import com.example.model.LessonProgress
import com.example.model.QuizAttemptRecord
import com.example.model.UserProfile
import com.example.model.VocabProgress
import com.example.repository.LocalAppPreferences
import com.example.ui.components.SilkRoadHeroBanner
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SuccessGreen

@Composable
fun ProgressAnalyticsScreen(
    profile: UserProfile,
    vocabProgress: List<VocabProgress>,
    lessonProgress: List<LessonProgress>,
    quizAttempts: List<QuizAttemptRecord>,
    onNavigateToSection: (String) -> Unit
) {
    val learnedWords = vocabProgress.count { it.learned }
    val totalWords = VocabularyCatalog.allWords.size
    val totalGrammar = GrammarAndSkillsCatalog.grammarLessons.size
    val completedGrammar = lessonProgress.count { it.skillType == "GRAMMAR" && it.completed }
    val completedListening = lessonProgress.count { it.skillType == "LISTENING" && it.completed }
    val completedSpeaking = lessonProgress.count { it.skillType == "SPEAKING" && it.completed }
    val completedReading = lessonProgress.count { it.skillType == "READING" && it.completed }
    val completedWriting = lessonProgress.count { it.skillType == "WRITING" && it.completed }

    val mistakeCounts = remember(quizAttempts) {
        quizAttempts
            .filter { !it.isCorrect && it.errorCategory.isNotBlank() && it.errorCategory != "NONE" }
            .groupingBy { it.errorCategory }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(5)
    }

    val currentEpochDay = System.currentTimeMillis() / (1000L * 60 * 60 * 24)
    val overdueVocabCount = vocabProgress.count { it.learned && it.nextReviewEpochDay <= currentEpochDay }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SilkRoadHeroBanner(
            title = "O'zlashtirish va Tahlil",
            subtitle = "Barcha natijalaringiz Cloud Firestore'da real vaqtda saqlanadi",
            cefrBadge = profile.cefrLevel
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Skill Progress Breakdown
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Analytics, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ko'nikmalar bo'yicha ko'rsatkichlar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                SkillProgressRow("Vocabulary (Lug'at)", learnedWords, totalWords)
                SkillProgressRow("Grammar (Grammatika)", completedGrammar, totalGrammar)
                SkillProgressRow("Listening (Tinglash)", completedListening, GrammarAndSkillsCatalog.listeningExercises.size)
                SkillProgressRow("Speaking (So'zlashish)", completedSpeaking, GrammarAndSkillsCatalog.speakingScenarios.size)
                SkillProgressRow("Reading (O'qish)", completedReading, GrammarAndSkillsCatalog.readingPassages.size)
                SkillProgressRow("Writing (Yozish)", completedWriting, GrammarAndSkillsCatalog.writingPrompts.size)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Personalized Revision Recommendations & Common Mistakes
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Shaxsiy Takrorlash Tavsiyalari",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (overdueVocabCount > 0) {
                    Text("• Lug'at: $overdueVocabCount ta so'zni Spaced Repetition orqali takrorlash vaqti keldi.")
                }

                if (mistakeCounts.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ko'p xato qilingan mavzular:",
                        style = MaterialTheme.typography.labelLarge,
                        color = ErrorRed
                    )
                    mistakeCounts.forEach { (category, count) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("⚠️ $category")
                            Text("$count marta xato", fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onNavigateToSection("GRAMMAR") }) {
                        Text("Xato mavzularni qayta mashq qilish")
                    }
                } else {
                    Text(
                        text = "Hozircha xatolar qayd etilmagan! Yangi dars va testlarni ishlashda davom eting.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Recent Activity Log
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "So'nggi test va mashq urinishlari (${quizAttempts.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (quizAttempts.isEmpty()) {
                    Text(
                        text = "Hali mashqlar bajarilmagan. Birinchi testni yechib ko'ring!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    quizAttempts.take(8).forEach { att ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (att.isCorrect) Icons.Default.CheckCircle else Icons.Default.WarningAmber,
                                    contentDescription = null,
                                    tint = if (att.isCorrect) SuccessGreen else ErrorRed
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${att.skillType} • Javob: ${att.submittedAnswer.take(40)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = att.feedbackUzbek.take(90),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = "${att.score}%",
                                    fontWeight = FontWeight.Bold,
                                    color = if (att.isCorrect) SuccessGreen else ErrorRed
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(88.dp))
    }
}

@Composable
private fun SkillProgressRow(label: String, done: Int, total: Int) {
    val ratio = (done.toFloat() / total.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(text = "$done / $total", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { ratio },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileSettingsScreen(
    profile: UserProfile,
    preferences: LocalAppPreferences,
    onSaveProfileSettings: (String, String, String, Long) -> Unit,
    onRetakePlacementTest: () -> Unit,
    onSetDarkTheme: (Boolean) -> Unit,
    onSetSlowTts: (Boolean) -> Unit,
    onSetShowUzbekTranslations: (Boolean) -> Unit,
    onSignOut: () -> Unit
) {
    var displayName by remember(profile.displayName) { mutableStateOf(profile.displayName) }
    var selectedCefr by remember(profile.cefrLevel) { mutableStateOf(profile.cefrLevel) }
    var learningGoal by remember(profile.learningGoal) { mutableStateOf(profile.learningGoal) }
    var dailyMinutes by remember(profile.dailyGoalMinutes) { mutableLongStateOf(profile.dailyGoalMinutes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Profil va O'quv Sozlamalari",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (profile.email.isNotBlank()) {
                    Text(
                        text = "Hisob: ${profile.email}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Ism va Familiya") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = learningGoal,
                    onValueChange = { learningGoal = it },
                    label = { Text("O'quv maqsadingiz") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Joriy CEFR darajangiz:",
                    style = MaterialTheme.typography.labelLarge
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CefrLevel.entries.forEach { lvl ->
                        FilterChip(
                            selected = selectedCefr == lvl.code,
                            onClick = { selectedCefr = lvl.code },
                            label = { Text(lvl.code) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Kunlik maqsad (daqiqa):",
                    style = MaterialTheme.typography.labelLarge
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(10L, 15L, 30L, 45L, 60L).forEach { mins ->
                        FilterChip(
                            selected = dailyMinutes == mins,
                            onClick = { dailyMinutes = mins },
                            label = { Text("$mins daq") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onSaveProfileSettings(displayName, selectedCefr, learningGoal, dailyMinutes)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("save_profile_settings_button")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Saqlash")
                    }
                    OutlinedButton(
                        onClick = onRetakePlacementTest,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Daraja testi")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // App Preferences (DataStore)
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Ilova Ko'rinishi va Audio Sozlamalari",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                SettingSwitchRow(
                    title = "Tungi rejim (Dark Theme)",
                    subtitle = "Ko'zni charchatmaydigan qorong'u mavzu",
                    checked = preferences.darkThemeEnabled,
                    onCheckedChange = onSetDarkTheme
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                SettingSwitchRow(
                    title = "Sekin audio talaffuz (0.72x)",
                    subtitle = "Inglizcha jumlalarni sekinroq tezlikda o'qish",
                    checked = preferences.slowTtsDefault,
                    onCheckedChange = onSetSlowTts
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                SettingSwitchRow(
                    title = "O'zbekcha tarjimalarni ochiq ko'rsatish",
                    subtitle = "Reading bo'limida tarjimalarni avtomatik ochish",
                    checked = preferences.showUzbekTranslationsDefault,
                    onCheckedChange = onSetShowUzbekTranslations
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSignOut,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("sign_out_button")
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Hisobdan chiqish (Sign Out)")
        }

        Spacer(modifier = Modifier.height(92.dp))
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
