package com.example.nutrition_assessment_system_android_app.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChartPie
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.example.nutrition_assessment_system_android_app.ui.common.component.bar.ProgressBar

@Composable
fun StatisticsCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Lucide.ChartPie,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(100))
                            .background(Color.Red)
                            .padding(4.dp)
                    )
                    Text(
                        text = "Nutrition"
                    )
                }
                Icon(
                    imageVector = Lucide.ChevronRight,
                    contentDescription = null
                )
            }
            StatisticsCardItem()
        }
    }
}

@Composable
fun StatisticsCardItem() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Calories"
            )
            Text(
                text = "109 / 198 g"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "55%"
            )
            ProgressBar(
                progress = 0.55f,
                showPercentage = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsCardPreview() {
    StatisticsCard()
}