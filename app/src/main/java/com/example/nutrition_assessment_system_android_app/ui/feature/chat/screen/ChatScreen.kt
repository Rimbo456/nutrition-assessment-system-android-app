package com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.BotMessageBubble
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.BotTypingIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.ChatInputBar
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.SuggestionChipsRow
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.UserMessageBubble
import kotlinx.coroutines.delay

private data class ChatMessage(
    val id: Long,
    val text: String,
    val isUser: Boolean,
    val time: String,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen() {
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var isBotTyping by remember { mutableStateOf(false) }

    val suggestions = listOf(
        "Phân tích dinh dưỡng của bữa ăn",
        "Tạo kế hoạch tập luyện",
        "Gợi ý thực đơn cho 1 ngày",
    )

    fun nowTime(): String {
        // Đơn giản: hiển thị hh:mm giả lập
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

    // Giả lập bot trả lời sau khi user gửi
    LaunchedEffect(messages.size) {
        if (messages.firstOrNull()?.isUser == true) {
            isBotTyping = true
            delay(800)
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                SuggestionChipsRow(
                    suggestions = suggestions,
                    onSuggestionClick = { prompt ->
                        // điền prompt vào input để user chỉnh/sent
                        inputText = prompt
                    },
                    modifier = Modifier.padding(bottom = 4.dp)
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
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                state = listState,
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                reverseLayout = true,
            ) {
                items(messages, key = { it.id }) { msg ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> fullHeight / 2 }
                        ) + fadeIn(),
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

                if (isBotTyping) {
                    item {
                        BotTypingIndicator()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen()
    }
}