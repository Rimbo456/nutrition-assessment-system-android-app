package com.example.nutrition_assessment_system_android_app.ui.feature.chat.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Send

/**
 * Thanh input ở cuối màn hình chat cho người dùng nhập tin nhắn.
 */
@Composable
fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Nhập tin nhắn...",
    enabled: Boolean = true,
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .background(colors.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 1.dp,
            color = colors.surface,
            border = BorderStroke(1.dp, colors.outlineVariant ?: colors.outline.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = colors.onSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = colors.onSurfaceVariant
                                )
                            )
                        }
                        innerTextField()
                    }
                )

                IconButton(
                    onClick = onSendClick,
                    enabled = enabled && value.isNotBlank(),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (enabled && value.isNotBlank()) colors.primary else colors.surfaceVariant,
                                shape = CircleShape
                            )
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Lucide.Send,
                            contentDescription = null,
                            tint = if (enabled && value.isNotBlank()) colors.onPrimary else colors.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatInputBarPreview() {
    MaterialTheme {
        val (text, setText) = remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 24.dp)
        ) {
            ChatInputBar(
                value = text,
                onValueChange = setText,
                onSendClick = {},
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
