package com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.History
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.example.nutrition_assessment_system_android_app.ui.common.component.bar.InternalNavigationBar
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.BotMessageBubble
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.BotTypingIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.ChatInputBar
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.SuggestionChipsRow
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.UserMessageBubble
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavController
) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var isBotTyping by remember { mutableStateOf(false) }

    val suggestions = listOf(
        "Phân tích dinh dưỡng của bữa ăn",
        "Tạo kế hoạch tập luyện",
        "Gợi ý thực đơn cho 1 ngày",
        "Theo dõi lượng nước uống"
    )

    fun nowTime(): String {
        return "09:2${messages.size}"
    }

    fun sendUserMessage(text: String) {
        if (text.isBlank()) return
        messages.add(0, ChatMessage(
            id = System.currentTimeMillis(),
            text = text.trim(),
            isUser = true,
            time = nowTime()
        ))
        inputText = ""
        isBotTyping = true
    }

    LaunchedEffect(messages.size) {
        if (messages.firstOrNull()?.isUser == true) {
            isBotTyping = true
            delay(1200)
            val lastUserText = messages.first().text
            val botReply = "Mình đã ghi nhận: \"$lastUserText\". Mình sẽ phân tích và gợi ý phù hợp cho bạn."
            messages.add(0, ChatMessage(
                id = System.currentTimeMillis(),
                text = botReply,
                isUser = false,
                time = nowTime()
            ))
            isBotTyping = false
        }
    }

    val listState = rememberLazyListState()
    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.background,
        topBar = {
            InternalNavigationBar(
                leftIcon = Lucide.ArrowLeft,
                leftIconInRightZone = Lucide.History,
                rightIconInRightZone = Lucide.Settings,
                onLeftButtonClick = { navController.popBackStack() },
                onLeftButtonInRightZoneClick = { TODO() },
                onRightButtonInRightZoneClick = { TODO() }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colors.background.copy(alpha = 0.0f),
                                colors.background.copy(alpha = 0.8f),
                                colors.background
                            )
                        )
                    )
            ) {
                SuggestionChipsRow(
                    suggestions = suggestions,
                    onSuggestionClick = { prompt ->
                        inputText = prompt
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ChatInputBar(
                    value = inputText,
                    onValueChange = { inputText = it },
                    onSendClick = { sendUserMessage(inputText) },
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.background,
                            colors.surface.copy(alpha = 0.3f),
                            colors.background
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isBotTyping) {
                    item(key = "typing_indicator") {
                        BotTypingIndicator()
                    }
                }

                items(messages, key = { it.id }) { msg ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> fullHeight / 3 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300)),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        if (msg.isUser) {
                            UserMessageBubble(
                                text = msg.text,
                                time = msg.time,
                            )
                        } else {
                            BotMessageBubble(
                                text = msg.text,
                                time = msg.time,
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class ChatMessage(
    val id: Long,
    val text: String,
    val isUser: Boolean,
    val time: String,
)

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
}