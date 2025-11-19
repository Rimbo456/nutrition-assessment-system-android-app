package com.example.nutrition_assessment_system_android_app.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Zap

@Composable
fun DailyIntakeCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Lucide.Zap,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(100))
                            .background(Color.Red)
                            .padding(4.dp)
                    )
                    Text(
                        text = "Daily Intake",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = "65%",
                    style = MaterialTheme.typography.displayLarge
                )
            }
            PieProgress(
                progress = 1250,
                sum = 1850,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyIntakeCardPreview() {
    DailyIntakeCard()
}