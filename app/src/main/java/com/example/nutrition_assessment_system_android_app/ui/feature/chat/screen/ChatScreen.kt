package com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel.ChatIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel.ChatViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun formatTime(ts: Long): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(ts))
    }

    // Trigger initial load
    LaunchedEffect(uiState.currentSession) {
        if (uiState.currentSession != null) {
            viewModel.onTriggerIntent(
                ChatIntent.LoadChatMessagesHistory(sessionId = uiState.currentSession!!.id, forceRefresh = true)
            )
        }
    }

    // Log state
    LaunchedEffect(uiState) {
        Log.d(
            "ChatScreen",
            "messages=${uiState.messages.size}, loading=${uiState.isLoading}, error=${uiState.errorMessage}"
        )
    }

    // Auto scroll to latest message when list grows
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            scope.launch { listState.animateScrollToItem(uiState.messages.lastIndex) }
        }
    }

    // Show error via snackbar
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { msg ->
            scope.launch { snackbarHostState.showSnackbar(msg) }
        }
    }

    val isAssistantTyping = uiState.isBotResponse

    val suggestions = listOf(
        "Phân tích dinh dưỡng của bữa ăn",
        "Tạo kế hoạch tập luyện",
        "Gợi ý thực đơn cho 1 ngày",
        "Theo dõi lượng nước uống"
    )

    Scaffold(
        containerColor = colors.background,
        topBar = {
            InternalNavigationBar(
                leftIcon = Lucide.ArrowLeft,
                leftIconInRightZone = Lucide.History,
                rightIconInRightZone = Lucide.Settings,
                onLeftButtonClick = {
                    viewModel.onTriggerIntent(ChatIntent.BackToChatSessionsList)
                    navController.popBackStack()
                },
                onLeftButtonInRightZoneClick = { /* TODO history */ },
                onRightButtonInRightZoneClick = { /* TODO settings */ }
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
                    .padding(top = 4.dp)
            ) {
                // Show suggestions only if no messages yet
                if (uiState.messages.isEmpty()) {
                    SuggestionChipsRow(
                        suggestions = suggestions,
                        onSuggestionClick = { prompt -> inputText = prompt },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                ChatInputBar(
                    value = inputText,
                    onValueChange = { inputText = it },
                    enabled = uiState.currentSession != null && !uiState.isLoading,
                    onSendClick = {
                        val trimmed = inputText.trim()
                        if (uiState.currentSession != null && trimmed.isNotEmpty()) {
                            viewModel.onTriggerIntent(ChatIntent.SendMessage(trimmed, uiState.currentSession!!.id))
                            inputText = "" // optimistic clear
                        }
                    },
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.background,
                            colors.surface.copy(alpha = 0.08f),
                            colors.background
                        )
                    )
                )
        ) {
            if (uiState.currentSession == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Không tìm thấy phiên trò chuyện", color = colors.error)
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(uiState.messages) { msg ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(250)
                        ) + fadeIn(animationSpec = tween(250)),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        if (msg.role == "user") {
                            UserMessageBubble(
                                text = msg.text,
                                time = formatTime(msg.createdAt)
                            )
                        } else {
                            BotMessageBubble(
                                text = msg.text,
                                time = formatTime(msg.createdAt)
                            )
                        }
                    }
                }
                if (isAssistantTyping) {
                    item(key = "assistant_typing") { BotTypingIndicator() }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    // Preview placeholder
}