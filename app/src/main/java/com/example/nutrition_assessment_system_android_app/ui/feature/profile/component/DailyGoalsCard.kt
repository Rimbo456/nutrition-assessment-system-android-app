package com.example.nutrition_assessment_system_android_app.ui.feature.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*

@Composable
fun DailyGoalsCard(
    targetCalories: Int,
    protein: Int,
    carbs: Int,
    fat: Int,
    water: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFFF97316).copy(alpha = 0.1f)
                ) {
                    Icon(
                        imageVector = Lucide.Flame,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp).size(16.dp),
                        tint = Color(0xFFF97316)
                    )
                }
                Text(
                    text = "Mục tiêu hàng ngày",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MacroGoalItem(
                    icon = Lucide.Flame,
                    label = "Calo",
                    value = targetCalories,
                    unit = "kcal",
                    color = Color(0xFFF97316)
                )
                MacroGoalItem(
                    icon = Lucide.Drumstick,
                    label = "Protein",
                    value = protein,
                    unit = "g",
                    color = Color(0xFFEF4444)
                )
                MacroGoalItem(
                    icon = Lucide.Wheat,
                    label = "Carbs",
                    value = carbs,
                    unit = "g",
                    color = Color(0xFFF59E0B)
                )
                MacroGoalItem(
                    icon = Lucide.Droplets,
                    label = "Fat",
                    value = fat,
                    unit = "g",
                    color = Color(0xFFEAB308)
                )
                MacroGoalItem(
                    icon = Lucide.Droplet,
                    label = "Nước",
                    value = water,
                    unit = "ml",
                    color = Color(0xFF3B82F6)
                )
            }
        }
    }
}

