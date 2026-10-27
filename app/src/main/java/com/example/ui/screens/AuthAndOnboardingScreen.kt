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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import com.example.data.GrammarAndSkillsCatalog
import com.example.firebase.attemptAutoSignIn
import com.example.firebase.onGoogleSignInClicked
import com.example.model.CefrLevel
import com.example.ui.components.DeterministicQuizEngine
import com.example.ui.components.SilkRoadHeroBanner
import com.example.ui.components.UstozBotAvatar

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        attemptAutoSignIn(
            context = context,
            credentialManager = credentialManager,
            onAuthSuccess = onAuthSuccess,
            onUnauthenticated = {},
            scope = coroutineScope
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SilkRoadHeroBanner(
            title = "UzEnglish AI",
            subtitle = "O'zbek tilida so'zlashuvchilar uchun sun'iy intellektga asoslangan ingliz tili maktabi (A1–C2)",
            cefrBadge = "A1 - C2"
        )

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UstozBotAvatar(size = 72.dp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Xush kelibsiz! / Welcome!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Shaxsiy o'quv rejangiz, o'zlashtirgan so'zlaringiz, test natijalaringiz va yutuqlaringiz bulutda xavfsiz saqlanishi uchun Google hisobingiz orqali tizimga kiring.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        onGoogleSignInClicked(
                            context = context,
                            credentialManager = credentialManager,
                            onAuthSuccess = {
                                isLoading = false
                                onAuthSuccess()
                            },
                            onAuthError = { msg ->
                                isLoading = false
                                errorMessage = msg
                            },
                            scope = coroutineScope,
                            onAuthCancelled = {
                                isLoading = false
                            }
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("google_sign_in_button"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Default.Person, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Sign in with Google",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Xavfsizlik eslatmasi: Ilovada Google Sign-In (Credential Manager) va Cloud Firestore avtomatik sozlangan. Qo'shimcha provayderlarni Firebase Console orqali boshqarish mumkin.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingPlacementScreen(
    initialName: String,
    onSpeakEnglish: (String) -> Unit,
    onCompleteOnboarding: (String, String, Long, Int, String) -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var displayName by remember(initialName) { mutableStateOf(initialName.ifBlank { "O'quvchi" }) }
    var selectedGoal by remember { mutableStateOf("Sayohat va kundalik muloqot") }
    var dailyMinutes by remember { mutableLongStateOf(15L) }
    var placementScore by remember { mutableIntStateOf(0) }
    var estimatedCefr by remember { mutableStateOf("A1") }

    val goals = listOf(
        "Sayohat va kundalik muloqot",
        "Xalqaro kompaniyada ishlash (IT & Biznes)",
        "IELTS va xorijiy universitetda o'qish",
        "Noldan boshlab mukammal o'rganish"
    )
    val minuteOptions = listOf(10L, 15L, 30L, 45L)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SilkRoadHeroBanner(
            title = "Boshlang'ich Sozlash va Daraja Testi",
            subtitle = "Maqsadingizni tanlang va A1–C2 darajangizni aniqlang",
            cefrBadge = estimatedCefr
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (step) {
            1 -> {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "1-qadam: Maqsadingiz va kunlik reja",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = displayName,
                            onValueChange = { displayName = it },
                            label = { Text("Ismingiz") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Ingliz tilini nima maqsadda o'rganmoqchisiz?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        goals.forEach { goal ->
                            FilterChip(
                                selected = selectedGoal == goal,
                                onClick = { selectedGoal = goal },
                                label = { Text(goal) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Kuniga necha daqiqa vaqt ajratasiz?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            minuteOptions.forEach { mins ->
                                FilterChip(
                                    selected = dailyMinutes == mins,
                                    onClick = { dailyMinutes = mins },
                                    label = { Text("$mins daqiqa") }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    placementScore = 0
                                    estimatedCefr = "A1"
                                    step = 3
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("A1 dan boshlash")
                            }
                            Button(
                                onClick = { step = 2 },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("start_placement_test_button")
                            ) {
                                Text("Daraja testini yechish")
                            }
                        }
                    }
                }
            }

            2 -> {
                Text(
                    text = "2-qadam: Qisqa darajani aniqlash testi (A1–C2)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "12 ta savolga javob bering. Natijaga qarab ilova sizning taxminiy CEFR darajangizni aniqlaydi.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                DeterministicQuizEngine(
                    questions = GrammarAndSkillsCatalog.placementQuestions,
                    skillType = "PLACEMENT",
                    onQuestionAnswered = { _, _, _ -> },
                    onQuizCompleted = { pct ->
                        placementScore = pct
                        estimatedCefr = GrammarAndSkillsCatalog.estimateCefrFromScore(pct)
                        step = 3
                    },
                    onAskAiHelp = { _, _ -> },
                    onSpeakEnglish = onSpeakEnglish
                )
            }

            3 -> {
                val cefrInfo = CefrLevel.fromCode(estimatedCefr)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sizning taxminiy darajangiz: ${cefrInfo.code}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${cefrInfo.titleUz} — ${cefrInfo.descriptionUz}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Eslatma: Bu dastlabki baholash natijasidir. Darajangizni istalgan vaqtda quyida yoki Profil bo'limida o'zgartirishingiz va testni qayta topshirishingiz mumkin.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Darajani qo'lda o'zgartirish (ixtiyoriy):",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CefrLevel.entries.forEach { lvl ->
                                FilterChip(
                                    selected = estimatedCefr == lvl.code,
                                    onClick = { estimatedCefr = lvl.code },
                                    label = { Text(lvl.code) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Tavsiya etilgan shaxsiy o'quv yo'nalishi:",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("• Grammatika: $estimatedCefr darajadagi asosiy qoidalar va mashqlar")
                                Text("• Lug'at: Kuniga 10 ta yangi so'z + Spaced Repetition takrorlash")
                                Text("• Amaliyot: Maqsadingiz (\"$selectedGoal\") bo'yicha AI Speaking va Writing")
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = {
                                onCompleteOnboarding(
                                    displayName,
                                    selectedGoal,
                                    dailyMinutes,
                                    placementScore,
                                    estimatedCefr
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("save_onboarding_button")
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Saqlash va O'qishni boshlash")
                        }
                    }
                }
            }
        }
    }
}
