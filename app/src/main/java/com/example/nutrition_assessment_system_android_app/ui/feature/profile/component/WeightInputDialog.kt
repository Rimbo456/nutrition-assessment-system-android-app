package com.example.nutrition_assessment_system_android_app.ui.feature.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import java.util.Locale

@Composable
fun WeightInputDialogComposable(
    currentWeight: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var weight by remember { mutableStateOf(currentWeight.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Lucide.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text("Cập nhật cân nặng")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Nhập cân nặng hiện tại của bạn để theo dõi tiến trình", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {
                        val newWeight = (weight.toDoubleOrNull() ?: 0.0) - 0.1
                        weight = String.format(Locale.getDefault(), "%.1f", newWeight)
                    }, modifier = Modifier.size(40.dp).clip(RoundedCornerShape(6.dp)).border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(6.dp))) {
                        Icon(Lucide.Settings, contentDescription = null)
                    }

                    TextField(value = weight, onValueChange = { weight = it }, modifier = Modifier.weight(1f).height(48.dp), textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold))

                    IconButton(onClick = {
                        val newWeight = (weight.toDoubleOrNull() ?: 0.0) + 0.1
                        weight = String.format(Locale.getDefault(), "%.1f", newWeight)
                    }, modifier = Modifier.size(40.dp).clip(RoundedCornerShape(6.dp)).border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(6.dp))) {
                        Icon(Lucide.Settings, contentDescription = null)
                    }
                }

                Text("Cân nặng trước: $currentWeight kg", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            }
        },
        confirmButton = {
            Button(onClick = { weight.toDoubleOrNull()?.let { onConfirm(it) } }) { Text("Lưu") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Hủy") }
        }
    )
}
