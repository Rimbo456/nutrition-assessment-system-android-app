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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotMessageBubble(
    text: String,
    time: String? = null,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Surface(
                modifier = Modifier.shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
                ),
                color = colors.surface,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    colors.surface,
                                    colors.surface.copy(alpha = 0.95f)
                                )
                            )
                        )
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        color = colors.onSurface,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            if (time != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time,
                    modifier = Modifier.padding(start = 12.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = colors.onSurfaceVariant.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    ),
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}
