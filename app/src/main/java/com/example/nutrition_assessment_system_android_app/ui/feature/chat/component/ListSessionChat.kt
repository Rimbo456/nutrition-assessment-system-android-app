package com.example.nutrition_assessment_system_android_app.ui.feature.chat.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession

@Composable
fun ListSessionChat(
    sessions: List<ChatSession>,
    modifier: Modifier = Modifier,
    onSelect: (ChatSession) -> Unit = {},
    onDelete: (ChatSession) -> Unit = {},
    onCreateNew: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme

    Surface(modifier = modifier.fillMaxSize(), color = colors.background) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(sessions, key = { it.id }) { session ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ListSessionChatItem(
                        session = session,
                        onClick = onSelect,
                        onDelete = onDelete,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
            item(key = "create_new") {
                Button(
                    onClick = onCreateNew,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primaryContainer, contentColor = colors.onPrimaryContainer)
                ) {
                    Icon(imageVector = Lucide.Plus, contentDescription = null)
                    Text(
                        text = "Tạo cuộc trò chuyện mới",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}