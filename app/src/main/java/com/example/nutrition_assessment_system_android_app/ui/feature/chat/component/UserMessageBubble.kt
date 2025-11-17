package com.example.nutrition_assessment_system_android_app.ui.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Bong bóng tin nhắn của người dùng (phía bên phải màn hình).
 */
@Composable
fun UserMessageBubble(
    text: String,
    time: String? = null,
    modifier: Modifier = Modifier,
    bubbleColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Surface(
                color = bubbleColor,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 2.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 2.dp,
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 18.sp
                    )
                )
            }

            if (time != null) {
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = time,
                    modifier = Modifier.padding(end = 4.dp, top = 2.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserMessageBubblePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 24.dp)
        ) {
            UserMessageBubble(
                text = "Dạ hôm nay em ăn sáng 2 ổ bánh mì với 1 ly sữa",
                time = "09:24"
            )
            UserMessageBubble(
                text = "Tổng năng lượng là khoảng 520 kcal phải không ạ?",
                time = "09:25"
            )
        }
    }
}
