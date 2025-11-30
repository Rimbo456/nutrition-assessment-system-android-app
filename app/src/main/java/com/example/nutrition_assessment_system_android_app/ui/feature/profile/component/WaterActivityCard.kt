package com.example.nutrition_assessment_system_android_app.ui.feature.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WaterActivityCard(
    waterIntake: Int,
    waterGoal: Int,
    steps: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)),
                        color = Color(0xFF1E90FF).copy(alpha = 0.12f)
                    ) {
                        Text("💧", color = Color(0xFF1E90FF), modifier = Modifier.padding(6.dp))
                    }
                    Column {
                        Text(text = "Nước hôm nay", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "$waterIntake/$waterGoal ly", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    repeat(waterGoal) { i ->
                        Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(
                            if (i < waterIntake) Color(0xFF1E90FF) else Color(0xFFDDDDDD)
                        ))
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)) {
                        Text("🏃", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(6.dp))
                    }
                    Column {
                        Text(text = "Hoạt động", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "$steps bước", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}
