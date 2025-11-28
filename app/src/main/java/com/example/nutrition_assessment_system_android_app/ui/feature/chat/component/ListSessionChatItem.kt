package com.example.nutrition_assessment_system_android_app.ui.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash2
import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListSessionChatItem(
    session: ChatSession,
    modifier: Modifier = Modifier,
    onClick: (ChatSession) -> Unit = {},
    onDelete: (ChatSession) -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme

    fun formatUpdatedAt(isoString: String): String {
        return try {
            val instant = java.time.Instant.parse(isoString)
            val localDateTime = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
            val formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm dd/MM")
            localDateTime.format(formatter)
        } catch (e: Exception) {
            isoString
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(session) },
        color = colors.surface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = session.title?.takeIf { it.isNotBlank() } ?: "Cuộc trò chuyện",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, color = colors.onSurface),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (session.lastMessage != null) {
                    Text(
                        text = session.lastMessage,
                        style = MaterialTheme.typography.bodySmall.copy(color = colors.onSurfaceVariant),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.alpha(0.9f)
                    )
                } else {
                    Text(
                        text = "Chưa có tin nhắn",
                        style = MaterialTheme.typography.bodySmall.copy(color = colors.onSurfaceVariant),
                        maxLines = 1,
                        modifier = Modifier.alpha(0.6f)
                    )
                }
                Text(
                    text = formatUpdatedAt(session.createdAt),
                    style = MaterialTheme.typography.labelSmall.copy(color = colors.onSurfaceVariant),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { onDelete(session) }) {
                Icon(
                    imageVector = Lucide.Trash2,
                    contentDescription = "Xóa phiên",
                    tint =colors.onSurfaceVariant
                )
            }
        }
    }
}
