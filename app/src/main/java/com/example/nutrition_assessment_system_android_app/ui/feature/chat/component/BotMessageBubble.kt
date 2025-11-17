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

@Composable
fun BotMessageBubble(
    text: String,
    time: String? = null,
    modifier: Modifier = Modifier,
    bubbleColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Surface(
                color = bubbleColor,
                shape = RoundedCornerShape(topStart = 2.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 1.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
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
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = time,
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BotMessageBubblePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {
            BotMessageBubble(
                text = "Hôm nay tổng năng lượng bạn ăn sáng khoảng 520 kcal.",
                time = "09:24"
            )
            UserMessageBubble(
                text = "Vậy là em đã đạt 30% nhu cầu năng lượng ngày rồi đúng không ạ?",
                time = "09:25"
            )
            BotMessageBubble(
                text = "Đúng rồi, bạn nên bổ sung thêm protein và rau vào bữa trưa nhé.",
                time = "09:26"
            )
        }
    }
}