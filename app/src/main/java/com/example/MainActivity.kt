package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ai.GeminiTutorService
import com.example.firebase.authStateFlow
import com.example.firebase.configureAppCheck
import com.example.firebase.signOutUser
import com.example.model.UiState
import com.example.model.UserProfile
import com.example.repository.LearningRepository
import com.example.repository.PreferencesRepository
import com.example.speech.SpeechAndTtsHelper
import com.example.ui.components.FloatingAiTutorOverlay
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.HomeDashboardScreen
import com.example.ui.screens.LearnHubScreen
import com.example.ui.screens.OnboardingPlacementScreen
import com.example.ui.screens.PracticeHubScreen
import com.example.ui.screens.ProfileSettingsScreen
import com.example.ui.screens.ProgressAnalyticsScreen
import com.example.ui.theme.UzEnglishTheme
import com.example.ui.viewmodel.UzEnglishViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Mandatory App Check configuration before UI, ViewModels, or Firebase calls
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot(auth: FirebaseAuth = Firebase.auth) {
    val context = LocalContext.current
    val prefsRepo = remember(context) { PreferencesRepository(context.applicationContext) }
    val appPrefs by prefsRepo.preferencesFlow.collectAsStateWithLifecycle(
        initialValue = com.example.repository.LocalAppPreferences()
    )

    val speechHelper = remember(context) { SpeechAndTtsHelper(context.applicationContext) }
    DisposableEffect(speechHelper) {
        onDispose { speechHelper.release() }
    }

    val currentUser by auth.authStateFlow().collectAsStateWithLifecycle(initialValue = auth.currentUser)

    UzEnglishTheme(darkTheme = appPrefs.darkThemeEnabled) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val user = currentUser
            if (user == null) {
                AuthScreen(onAuthSuccess = {})
            } else {
                AuthenticatedAppContent(
                    currentUserId = user.uid,
                    initialDisplayName = user.displayName.orEmpty(),
                    initialEmail = user.email.orEmpty(),
                    speechHelper = speechHelper
                )
            }
        }
    }
}

private enum class MainBottomTab(val labelUz: String) {
    HOME("Bosh sahifa"),
    LEARN("O'rganish"),
    PRACTICE("Amaliyot"),
    PROGRESS("Natijalar"),
    PROFILE("Profil")
}

@Composable
fun AuthenticatedAppContent(
    currentUserId: String,
    initialDisplayName: String,
    initialEmail: String,
    speechHelper: SpeechAndTtsHelper
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }

    val viewModel: UzEnglishViewModel = viewModel(
        key = currentUserId,
        factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) {
                    "APPLICATION_KEY missing from CreationExtras"
                }
                val databaseId = app.getString(R.string.firestore_database_id)
                val db = FirebaseFirestore.getInstance(databaseId)
                UzEnglishViewModel(
                    repository = LearningRepository(db),
                    preferencesRepository = PreferencesRepository(app),
                    aiService = GeminiTutorService(),
                    currentUserId = currentUserId,
                    initialDisplayName = initialDisplayName,
                    initialEmail = initialEmail
                )
            }
        }
    )

    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val vocabProgress by viewModel.vocabProgressList.collectAsStateWithLifecycle()
    val lessonProgress by viewModel.lessonProgressList.collectAsStateWithLifecycle()
    val quizAttempts by viewModel.quizAttemptsList.collectAsStateWithLifecycle()
    val appPreferences by viewModel.appPreferences.collectAsStateWithLifecycle()
    val tutorContext by viewModel.tutorContext.collectAsStateWithLifecycle()
    val isTutorOpen by viewModel.isTutorOpen.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val isTutorLoading by viewModel.isTutorLoading.collectAsStateWithLifecycle()
    val writingReviewState by viewModel.writingReviewState.collectAsStateWithLifecycle()
    val speakingFeedbackState by viewModel.speakingFeedbackState.collectAsStateWithLifecycle()
    val grammarAiState by viewModel.grammarAiExplanationState.collectAsStateWithLifecycle()
    val extraQuestionsByLesson by viewModel.extraQuestionsByLesson.collectAsStateWithLifecycle()
    val statusBanner by viewModel.statusBannerMessage.collectAsStateWithLifecycle()

    var currentTab by remember { mutableStateOf(MainBottomTab.HOME) }
    var learnSubTab by remember { mutableStateOf("GRAMMAR") }
    var practiceSubTab by remember { mutableStateOf("LISTENING") }
    var showPlacementFlow by remember { mutableStateOf(false) }

    BackHandler(enabled = showPlacementFlow || isTutorOpen || currentTab != MainBottomTab.HOME) {
        when {
            isTutorOpen -> viewModel.setTutorOpen(false)
            showPlacementFlow -> showPlacementFlow = false
            else -> currentTab = MainBottomTab.HOME
        }
    }

    val navigateToControlledRoute: (String) -> Unit = { route ->
        when (route.uppercase()) {
            "GRAMMAR" -> {
                learnSubTab = "GRAMMAR"
                currentTab = MainBottomTab.LEARN
            }
            "VOCABULARY" -> {
                learnSubTab = "VOCABULARY"
                currentTab = MainBottomTab.LEARN
            }
            "LISTENING", "SPEAKING", "READING", "WRITING" -> {
                practiceSubTab = route.uppercase()
                currentTab = MainBottomTab.PRACTICE
            }
            "PLACEMENT" -> {
                showPlacementFlow = true
            }
            "PROGRESS" -> {
                currentTab = MainBottomTab.PROGRESS
            }
            "PROFILE" -> {
                currentTab = MainBottomTab.PROFILE
            }
        }
    }

    val activeProfile: UserProfile = when (val s = profileState) {
        is UiState.Success -> s.data
        else -> UserProfile(
            id = currentUserId,
            userId = currentUserId,
            displayName = initialDisplayName.ifBlank { "O'quvchi" },
            email = initialEmail,
            placementCompleted = true
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentTab == MainBottomTab.HOME && !showPlacementFlow,
                    onClick = {
                        showPlacementFlow = false
                        currentTab = MainBottomTab.HOME
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Bosh sahifa") },
                    label = { Text("Home") },
                    modifier = Modifier.testTag("nav_home")
                )
                NavigationBarItem(
                    selected = currentTab == MainBottomTab.LEARN && !showPlacementFlow,
                    onClick = {
                        showPlacementFlow = false
                        currentTab = MainBottomTab.LEARN
                    },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "O'rganish") },
                    label = { Text("Learn") },
                    modifier = Modifier.testTag("nav_learn")
                )
                NavigationBarItem(
                    selected = currentTab == MainBottomTab.PRACTICE && !showPlacementFlow,
                    onClick = {
                        showPlacementFlow = false
                        currentTab = MainBottomTab.PRACTICE
                    },
                    icon = { Icon(Icons.Default.RecordVoiceOver, contentDescription = "Amaliyot") },
                    label = { Text("Practice") },
                    modifier = Modifier.testTag("nav_practice")
                )
                NavigationBarItem(
                    selected = currentTab == MainBottomTab.PROGRESS && !showPlacementFlow,
                    onClick = {
                        showPlacementFlow = false
                        currentTab = MainBottomTab.PROGRESS
                    },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = "Natijalar") },
                    label = { Text("Progress") },
                    modifier = Modifier.testTag("nav_progress")
                )
                NavigationBarItem(
                    selected = currentTab == MainBottomTab.PROFILE && !showPlacementFlow,
                    onClick = {
                        showPlacementFlow = false
                        currentTab = MainBottomTab.PROFILE
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profile") },
                    modifier = Modifier.testTag("nav_profile")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (statusBanner != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = statusBanner ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { viewModel.clearStatusBanner() }) {
                                Icon(Icons.Default.Close, contentDescription = "Yopish")
                            }
                        }
                    }
                }

                when {
                    profileState is UiState.Loading && vocabProgress.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    !activeProfile.placementCompleted || showPlacementFlow -> {
                        OnboardingPlacementScreen(
                            initialName = activeProfile.displayName,
                            onSpeakEnglish = { text ->
                                speechHelper.speakEnglish(text, appPreferences.slowTtsDefault)
                            },
                            onCompleteOnboarding = { name, goal, mins, score, cefr ->
                                viewModel.completeOnboardingAndPlacement(name, goal, mins, score, cefr)
                                showPlacementFlow = false
                                currentTab = MainBottomTab.HOME
                            }
                        )
                    }

                    else -> {
                        when (currentTab) {
                            MainBottomTab.HOME -> {
                                viewModel.updateTutorContext("Bosh sahifa", "Dashboard", "")
                                HomeDashboardScreen(
                                    profile = activeProfile,
                                    vocabProgress = vocabProgress,
                                    lessonProgress = lessonProgress,
                                    onNavigateToSection = navigateToControlledRoute,
                                    onOpenPlacementTest = { showPlacementFlow = true },
                                    onOpenAiTutor = { viewModel.setTutorOpen(true) }
                                )
                            }

                            MainBottomTab.LEARN -> {
                                LearnHubScreen(
                                    initialSubTab = learnSubTab,
                                    userCefrLevel = activeProfile.cefrLevel,
                                    vocabProgress = vocabProgress,
                                    lessonProgress = lessonProgress,
                                    extraQuestionsByLesson = extraQuestionsByLesson,
                                    grammarAiExplanationState = grammarAiState,
                                    onSpeakEnglish = { text, slow ->
                                        speechHelper.speakEnglish(text, slow || appPreferences.slowTtsDefault)
                                    },
                                    onToggleWordLearned = { viewModel.toggleWordLearned(it) },
                                    onToggleWordFavorite = { viewModel.toggleWordFavorite(it) },
                                    onRecordSpacedRepetition = { word, ok ->
                                        viewModel.recordSpacedRepetitionReview(word, ok)
                                    },
                                    onRecordQuestionAttempt = { q, skill, ans, ok ->
                                        viewModel.recordQuestionAttempt(q, skill, ans, ok)
                                    },
                                    onCompleteLessonQuiz = { id, skill, title, pct ->
                                        viewModel.completeLessonQuiz(id, skill, title, pct)
                                    },
                                    onRequestGrammarAiExplanation = { title, q ->
                                        viewModel.requestGrammarAiPersonalization(title, q)
                                    },
                                    onGenerateExtraGrammarQuestion = { id, title ->
                                        viewModel.generateExtraGrammarQuestion(id, title)
                                    },
                                    onUpdateTutorContext = { scr, les, q ->
                                        viewModel.updateTutorContext(scr, les, q)
                                    },
                                    onOpenAiTutor = { viewModel.setTutorOpen(true) }
                                )
                            }

                            MainBottomTab.PRACTICE -> {
                                PracticeHubScreen(
                                    initialTab = practiceSubTab,
                                    speechHelper = speechHelper,
                                    defaultShowUzbek = appPreferences.showUzbekTranslationsDefault,
                                    writingReviewState = writingReviewState,
                                    speakingFeedbackState = speakingFeedbackState,
                                    onSpeakEnglish = { text, slow ->
                                        speechHelper.speakEnglish(text, slow || appPreferences.slowTtsDefault)
                                    },
                                    onRecordQuestionAttempt = { q, skill, ans, ok ->
                                        viewModel.recordQuestionAttempt(q, skill, ans, ok)
                                    },
                                    onCompleteLessonQuiz = { id, skill, title, pct ->
                                        viewModel.completeLessonQuiz(id, skill, title, pct)
                                    },
                                    onSubmitWritingForAi = { id, title, instr, text ->
                                        viewModel.submitWritingForAiReview(id, title, instr, text)
                                    },
                                    onResetWritingReview = { viewModel.resetWritingReview() },
                                    onSubmitSpeakingForAi = { id, title, prompt, text ->
                                        viewModel.submitSpeakingForAiFeedback(id, title, prompt, text)
                                    },
                                    onResetSpeakingFeedback = { viewModel.resetSpeakingFeedback() },
                                    onSaveWordFromGlossary = { w, m, p ->
                                        viewModel.saveWordFromReadingGlossary(w, m, p)
                                    },
                                    onUpdateTutorContext = { scr, les, q ->
                                        viewModel.updateTutorContext(scr, les, q)
                                    },
                                    onOpenAiTutor = { viewModel.setTutorOpen(true) }
                                )
                            }

                            MainBottomTab.PROGRESS -> {
                                viewModel.updateTutorContext("Natijalar va Tahlil", "Progress Analytics", "")
                                ProgressAnalyticsScreen(
                                    profile = activeProfile,
                                    vocabProgress = vocabProgress,
                                    lessonProgress = lessonProgress,
                                    quizAttempts = quizAttempts,
                                    onNavigateToSection = navigateToControlledRoute
                                )
                            }

                            MainBottomTab.PROFILE -> {
                                viewModel.updateTutorContext("Profil va Sozlamalar", "User Settings", "")
                                ProfileSettingsScreen(
                                    profile = activeProfile,
                                    preferences = appPreferences,
                                    onSaveProfileSettings = { name, cefr, goal, mins ->
                                        viewModel.updateProfileSettings(name, cefr, goal, mins)
                                    },
                                    onRetakePlacementTest = { showPlacementFlow = true },
                                    onSetDarkTheme = { viewModel.setDarkTheme(it) },
                                    onSetSlowTts = { viewModel.setSlowTtsDefault(it) },
                                    onSetShowUzbekTranslations = { viewModel.setShowUzbekTranslations(it) },
                                    onSignOut = {
                                        signOutUser(
                                            credentialManager = credentialManager,
                                            onSignOutComplete = {},
                                            scope = scope
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Floating AI Tutor Robot & Contextual Chat Overlay
            FloatingAiTutorOverlay(
                tutorContext = tutorContext,
                isOpen = isTutorOpen,
                onToggleOpen = { viewModel.setTutorOpen(it) },
                messages = chatMessages,
                isLoading = isTutorLoading,
                initialOffsetX = appPreferences.robotOffsetX,
                initialOffsetY = appPreferences.robotOffsetY,
                onSaveOffset = { x, y -> viewModel.saveRobotPosition(x, y) },
                onSendMessage = { viewModel.sendTutorMessage(it) },
                onNavigateToSection = navigateToControlledRoute
            )
        }
    }
}
