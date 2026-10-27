package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.ChatMessage
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.model.TutorContext
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SuccessGreen
import kotlin.math.roundToInt

@Composable
fun SilkRoadHeroBanner(
    title: String,
    subtitle: String,
    cefrBadge: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(156.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF0A2E6E), Color(0xFF00695C), Color(0xFF1565C0)),
                    start = Offset.Zero,
                    end = Offset(w, h)
                )
            )
            // Decorative golden stars and Silk Road arcs
            drawCircle(
                color = Color(0x33FFD54F),
                radius = h * 0.65f,
                center = Offset(w * 0.85f, h * 0.25f)
            )
            drawCircle(
                color = Color(0x224DB6AC),
                radius = h * 0.5f,
                center = Offset(w * 0.15f, h * 0.85f)
            )
            // Stylized Registan dome silhouette on right
            drawArc(
                color = Color(0x4400E5FF),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.72f, h * 0.45f),
                size = Size(w * 0.18f, h * 0.6f)
            )
            drawRect(
                color = Color(0x33FFFFFF),
                topLeft = Offset(w * 0.70f, h * 0.72f),
                size = Size(w * 0.22f, h * 0.28f)
            )
            // Connecting golden bridge arc
            val bridgePath = Path().apply {
                moveTo(w * 0.05f, h * 0.92f)
                quadraticTo(w * 0.5f, h * 0.55f, w * 0.95f, h * 0.92f)
            }
            drawPath(
                path = bridgePath,
                color = Color(0x55FFD54F),
                style = Stroke(width = 3.dp.toPx())
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0x33FFFFFF),
                    shape = RoundedCornerShape(50),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66FFD54F))
                ) {
                    Text(
                        text = "O'zbek -> English AI",
                        color = Color(0xFFFFECB3),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = Color(0xFFFFB300),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Daraja: $cefrBadge",
                        color = Color(0xFF1A1200),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = Color(0xFFE0F2F1),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun UstozBotAvatar(
    size: Dp = 52.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF0D47A1), Color(0xFF00897B))
                )
            )
            .border(2.dp, Color(0xFFFFD54F), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height
            // Antenna
            drawLine(
                color = Color(0xFFFFD54F),
                start = Offset(w * 0.5f, h * 0.14f),
                end = Offset(w * 0.5f, h * 0.27f),
                strokeWidth = w * 0.05f
            )
            drawCircle(
                color = Color(0xFFFFD54F),
                radius = w * 0.06f,
                center = Offset(w * 0.5f, h * 0.12f)
            )
            // Robot head
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(w * 0.22f, h * 0.27f),
                size = Size(w * 0.56f, h * 0.48f),
                cornerRadius = CornerRadius(w * 0.14f, w * 0.14f)
            )
            // Screen visor
            drawRoundRect(
                color = Color(0xFF0D1B2A),
                topLeft = Offset(w * 0.28f, h * 0.35f),
                size = Size(w * 0.44f, h * 0.24f),
                cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
            )
            // Glowing cyan eyes
            drawCircle(
                color = Color(0xFF00E5FF),
                radius = w * 0.055f,
                center = Offset(w * 0.39f, h * 0.47f)
            )
            drawCircle(
                color = Color(0xFF00E5FF),
                radius = w * 0.055f,
                center = Offset(w * 0.61f, h * 0.47f)
            )
            // Friendly smile arc
            drawArc(
                color = Color(0xFF00796B),
                startAngle = 20f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = Offset(w * 0.38f, h * 0.58f),
                size = Size(w * 0.24f, h * 0.11f),
                style = Stroke(width = w * 0.04f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FloatingAiTutorOverlay(
    tutorContext: TutorContext,
    isOpen: Boolean,
    onToggleOpen: (Boolean) -> Unit,
    messages: List<ChatMessage>,
    isLoading: Boolean,
    initialOffsetX: Float,
    initialOffsetY: Float,
    onSaveOffset: (Float, Float) -> Unit,
    onSendMessage: (String) -> Unit,
    onNavigateToSection: (String) -> Unit
) {
    var offsetX by remember(initialOffsetX) { mutableStateOf(initialOffsetX) }
    var offsetY by remember(initialOffsetY) { mutableStateOf(initialOffsetY) }
    var inputMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Draggable Floating Robot Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 92.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            onSaveOffset(offsetX, offsetY)
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX = (offsetX + dragAmount.x).coerceIn(-750f, 40f)
                            offsetY = (offsetY + dragAmount.y).coerceIn(-1200f, 80f)
                        }
                    )
                }
                .shadow(10.dp, CircleShape)
                .clickable { onToggleOpen(!isOpen) }
                .testTag("floating_ai_tutor_button")
        ) {
            UstozBotAvatar(size = 58.dp)
        }

        // Compact Chat Panel when opened
        AnimatedVisibility(
            visible = isOpen,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 12.dp, vertical = 84.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 320.dp, max = 480.dp)
                    .border(
                        1.5.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        RoundedCornerShape(24.dp)
                    )
                    .testTag("ai_tutor_chat_panel"),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            UstozBotAvatar(size = 38.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "UstozBot — AI Yordamchi",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Kontekst: ${tutorContext.screenName} (${tutorContext.userLevel})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(
                            onClick = { onToggleOpen(false) },
                            modifier = Modifier.testTag("close_tutor_panel_button")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Yopish")
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                    // Quick Action Chips
                    val quickActions = listOf(
                        "Qoidani tushuntir" to "Shu darsdagi asosiy grammatik qoidani sodda o'zbek tilida misollar bilan tushuntirib ber.",
                        "Shu savolga yordam" to "Hozirgi savolni yechish uchun tayyor javobni aytmasdan, foydali ishora (hint) ber.",
                        "Javobimni tekshir" to "Mening javobimni tekshir va xatolarim bo'lsa sababini tushuntir.",
                        "Inglizcha suhbat" to "Keling, darajamga mos mavzuda qisqa inglizcha suhbat (role-play) qilamiz.",
                        "Bo'limni ochish" to "Yozish mashqi (Writing) bo'limini ochib ber."
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        quickActions.forEach { (label, prompt) ->
                            AssistChip(
                                onClick = { onSendMessage(prompt) },
                                label = {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Messages Thread
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(messages, key = { it.id }) { msg ->
                            val isUser = msg.isUser
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                            ) {
                                Surface(
                                    color = if (isUser) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isUser) 16.dp else 4.dp,
                                        bottomEnd = if (isUser) 4.dp else 16.dp
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = msg.text,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isUser) {
                                                MaterialTheme.colorScheme.onPrimary
                                            } else {
                                                MaterialTheme.colorScheme.onSurface
                                            }
                                        )
                                        if (!msg.suggestedActionRoute.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Button(
                                                onClick = {
                                                    onToggleOpen(false)
                                                    onNavigateToSection(msg.suggestedActionRoute)
                                                }
                                            ) {
                                                Text(msg.suggestedActionLabel ?: "Bo'limga o'tish")
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    Icons.AutoMirrored.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (isLoading) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "UstozBot javob yozmoqda...",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputMessage,
                            onValueChange = { inputMessage = it },
                            placeholder = { Text("Savolingizni yozing...") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("tutor_chat_input"),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (inputMessage.isNotBlank()) {
                                    onSendMessage(inputMessage)
                                    inputMessage = ""
                                }
                            },
                            enabled = inputMessage.isNotBlank() && !isLoading,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .testTag("tutor_chat_send_button")
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Yuborish",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeterministicQuizEngine(
    questions: List<QuizQuestion>,
    skillType: String,
    onQuestionAnswered: (QuizQuestion, String, Boolean) -> Unit,
    onQuizCompleted: (Int) -> Unit,
    onAskAiHelp: (QuizQuestion, String) -> Unit,
    onSpeakEnglish: (String) -> Unit
) {
    if (questions.isEmpty()) return

    var currentIndex by remember(questions) { mutableIntStateOf(0) }
    var selectedAnswer by remember(currentIndex, questions) { mutableStateOf("") }
    var isChecked by remember(currentIndex, questions) { mutableStateOf(false) }
    var isCorrect by remember(currentIndex, questions) { mutableStateOf(false) }
    var correctCount by remember(questions) { mutableIntStateOf(0) }
    var quizFinished by remember(questions) { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex) ?: return

    if (quizFinished) {
        val percentage = ((correctCount.toFloat() / questions.size) * 100).roundToInt()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mashq yakunlandi! Natija: $percentage%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${questions.size} ta savoldan $correctCount tasi to'g'ri topildi.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {
                        currentIndex = 0
                        correctCount = 0
                        selectedAnswer = ""
                        isChecked = false
                        quizFinished = false
                    },
                    modifier = Modifier.testTag("retake_quiz_button")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Qayta ishlash")
                }
            }
        }
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Savol ${currentIndex + 1} / ${questions.size} (${currentQuestion.cefrLevel})",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    IconButton(onClick = { onSpeakEnglish(currentQuestion.promptEn) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Savolni eshitish",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = { onAskAiHelp(currentQuestion, selectedAnswer) },
                        modifier = Modifier.testTag("ask_ai_about_question_button")
                    ) {
                        Icon(
                            Icons.Default.HelpOutline,
                            contentDescription = "UstozBotdan so'rash",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = currentQuestion.promptEn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = currentQuestion.promptUz,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (currentQuestion.type == QuestionType.TYPED_ANSWER || currentQuestion.options.isEmpty()) {
                OutlinedTextField(
                    value = selectedAnswer,
                    onValueChange = { if (!isChecked) selectedAnswer = it },
                    label = { Text("Javobingizni ingliz tilida yozing") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isChecked,
                    singleLine = true
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    currentQuestion.options.forEach { option ->
                        val isSelected = selectedAnswer == option
                        val borderColor = when {
                            isChecked && option.equals(currentQuestion.correctAnswer, ignoreCase = true) -> SuccessGreen
                            isChecked && isSelected && !isCorrect -> ErrorRed
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isChecked) { selectedAnswer = option },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                            },
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            if (isChecked) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = if (isCorrect) SuccessGreen.copy(alpha = 0.12f) else ErrorRed.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Error,
                                contentDescription = null,
                                tint = if (isCorrect) SuccessGreen else ErrorRed
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCorrect) "Barakalla! Javobingiz to'g'ri!" else "To'g'ri javob: ${currentQuestion.correctAnswer}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) SuccessGreen else ErrorRed
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentQuestion.explanationUz,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (!isCorrect) {
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedButton(
                                onClick = { onAskAiHelp(currentQuestion, selectedAnswer) }
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Nega xato bo'lganini UstozBotdan so'rash")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isChecked) {
                    Button(
                        onClick = {
                            isCorrect = selectedAnswer.trim().equals(currentQuestion.correctAnswer.trim(), ignoreCase = true)
                            isChecked = true
                            if (isCorrect) correctCount++
                            onQuestionAnswered(currentQuestion, selectedAnswer, isCorrect)
                        },
                        enabled = selectedAnswer.isNotBlank(),
                        modifier = Modifier.testTag("check_quiz_answer_button")
                    ) {
                        Text("Javobni tekshirish")
                    }
                } else {
                    Button(
                        onClick = {
                            if (currentIndex + 1 < questions.size) {
                                currentIndex++
                            } else {
                                val finalPct = ((correctCount.toFloat() / questions.size) * 100).roundToInt()
                                quizFinished = true
                                onQuizCompleted(finalPct)
                            }
                        },
                        modifier = Modifier.testTag("next_quiz_question_button")
                    ) {
                        Text(if (currentIndex + 1 < questions.size) "Keyingi savol" else "Yakunlash")
                    }
                }
            }
        }
    }
}
