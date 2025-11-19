package com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition_assessment_system_android_app.R

/**
 * Màn hình pre-chat: giới thiệu tính năng trợ lý dinh dưỡng và nút bắt đầu trò chuyện.
 */
@Composable
fun PreChatScreen(
    modifier: Modifier = Modifier,
    onStartChat: () -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 75.dp),
        color = colors.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.surfaceVariant)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Hình minh hoạ (tuỳ bạn map R.drawable tương ứng, nếu chưa có có thể tạm ẩn)
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(colors.surface, RoundedCornerShape(100.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Nếu bạn có illustration, thay painterResource tương ứng
                    // Image(
                    //    painter = painterResource(id = R.drawable.ic_chat_nutrition),
                    //    contentDescription = null,
                    //    modifier = Modifier.fillMaxSize(0.8f),
                    //    contentScale = ContentScale.Fit
                    // )
                    Text(
                        text = "NAS",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Trợ lý dinh dưỡng cá nhân",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = colors.onSurface,
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Trao đổi với trợ lý để phân tích bữa ăn, gợi ý thực đơn và lập kế hoạch tập luyện phù hợp với bạn.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colors.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colors.surface,
                    tonalElevation = 1.dp,
                    shadowElevation = 0.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Bạn có thể hỏi nhanh:",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = colors.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "• Phân tích dinh dưỡng của bữa ăn hôm nay",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colors.onSurface
                            )
                        )
                        Text(
                            text = "• Gợi ý thực đơn 1 ngày cho người giảm cân",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colors.onSurface
                            )
                        )
                        Text(
                            text = "• Tạo kế hoạch tập luyện phù hợp với thể trạng",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colors.onSurface
                            )
                        )
                    }
                }
            }

            Button(
                onClick = onStartChat,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(
                    text = "Bắt đầu trò chuyện",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
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

