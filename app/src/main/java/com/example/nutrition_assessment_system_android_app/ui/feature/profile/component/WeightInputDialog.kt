package com.example.nutrition_assessment_system_android_app.ui.feature.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.*
import java.util.Locale

@Composable
fun WeightInputDialogComposable(
    currentWeight: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var weight by remember { mutableStateOf(String.format(Locale.getDefault(), "%.1f", currentWeight)) }
    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        imageVector = Lucide.Scale,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Cập nhật cân nặng",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Nhập cân nặng hiện tại của bạn",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Weight input with +/- buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilledTonalIconButton(
                        onClick = {
                            val newWeight = (weight.toDoubleOrNull() ?: 0.0) - 0.1
                            if (newWeight > 0) {
                                weight = String.format(Locale.getDefault(), "%.1f", newWeight)
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Lucide.Minus, contentDescription = "Giảm")
                    }

                    OutlinedTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                            isError = it.toDoubleOrNull() == null || it.toDoubleOrNull()!! <= 0
                        },
                        modifier = Modifier.weight(1f),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        suffix = {
                            Text(
                                "kg",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 16.sp
                            )
                        },
                        isError = isError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        shape = MaterialTheme.shapes.large
                    )

                    FilledTonalIconButton(
                        onClick = {
                            val newWeight = (weight.toDoubleOrNull() ?: 0.0) + 0.1
                            weight = String.format(Locale.getDefault(), "%.1f", newWeight)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Lucide.Plus, contentDescription = "Tăng")
                    }
                }

                if (isError) {
                    Text(
                        "Vui lòng nhập cân nặng hợp lệ",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Previous weight
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "Cân nặng trước: ${String.format(Locale.getDefault(), "%.1f", currentWeight)} kg",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Hủy")
                    }
                    Button(
                        onClick = {
                            val newWeight = weight.toDoubleOrNull()
                            if (newWeight != null && newWeight > 0) {
                                onConfirm(newWeight)
                            }
                        },
                        enabled = !isError && weight.isNotEmpty(),
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Lưu")
                    }
                }
            }
        }
    }
}

