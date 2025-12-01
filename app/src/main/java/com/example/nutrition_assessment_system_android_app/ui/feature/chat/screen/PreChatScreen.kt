package com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.component.*
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel.ChatIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel.ChatViewModel
import java.text.SimpleDateFormat
import java.util.*

// Helper function
private fun formatTimeAgo(timestamp: String?): String {
    if (timestamp == null) return "Chưa cập nhật"

    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(timestamp) ?: return "Không xác định"

        val now = System.currentTimeMillis()
        val diff = now - date.time

        val minutes = diff / (1000 * 60)
        val hours = diff / (1000 * 60 * 60)
        val days = diff / (1000 * 60 * 60 * 24)

        when {
            minutes < 1 -> "Vừa xong"
            minutes < 60 -> "$minutes phút trước"
            hours < 24 -> "$hours giờ trước"
            days < 7 -> "$days ngày trước"
            else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
        }
    } catch (_: Exception) {
        "Không xác định"
    }
}

/**
 * Màn hình pre-chat: hiển thị danh sách chat sessions
 */
@Composable
fun PreChatScreen(
    modifier: Modifier = Modifier,
    onStartChat: (String) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onTriggerIntent(ChatIntent.LoadChatSessionHistory(forceRefresh = true))
    }

    LaunchedEffect(uiState.currentSession) {
        uiState.currentSession?.let { session ->
            onStartChat(session.id)
        }
    }

    // Filter sessions
    val filteredSessions = remember(uiState.allSessions, searchQuery) {
        if (searchQuery.isEmpty()) {
            uiState.allSessions
        } else {
            uiState.allSessions.filter { session ->
                session.title?.contains(searchQuery, ignoreCase = true) == true ||
                session.lastMessage?.contains(searchQuery, ignoreCase = true) == true
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            ChatSessionListHeader(
                sessionCount = uiState.allSessions.size,
                onNewChat = {
                    viewModel.onTriggerIntent(ChatIntent.CreateNewChatSession("chat"))
                }
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

            // Search
            ChatSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            // Content
            if (uiState.isLoading) {
                // Loading state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else if (filteredSessions.isEmpty()) {
                // Empty state
                ChatEmptyState(
                    isSearching = searchQuery.isNotEmpty(),
                    onNewChat = if (searchQuery.isEmpty()) {
                        { viewModel.onTriggerIntent(ChatIntent.CreateNewChatSession("chat")) }
                    } else null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Session list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = filteredSessions,
                        key = { it.id }
                    ) { session ->
                        ChatSessionItem(
                            sessionId = session.id,
                            title = session.title ?: "Cuộc trò chuyện",
                            lastMessage = session.lastMessage ?: "Chưa có tin nhắn",
                            updatedAt = formatTimeAgo(session.updatedAt),
                            isActive = uiState.currentSession?.id == session.id,
                            onSelect = {
                                viewModel.onTriggerIntent(ChatIntent.SelectChatSession(session))
                            },
                            onEdit = {
                                // TODO: Implement edit
                            },
                            onDelete = {
                                // TODO: Implement delete
                            }
                        )
                    }
                }
            }

            // Footer
            if (!uiState.isLoading && filteredSessions.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nhấn vào cuộc trò chuyện để tiếp tục",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreChatScreenPreview() {
    MaterialTheme {
        PreChatScreen(onStartChat = {})
    }
}

