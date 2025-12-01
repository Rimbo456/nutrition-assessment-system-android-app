package com.example.nutrition_assessment_system_android_app.ui.feature.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutrition_assessment_system_android_app.ui.common.component.chart.BarChart
import com.example.nutrition_assessment_system_android_app.ui.common.component.chart.BarEntry

@Composable
fun WeightJourneyCard(
    modifier: Modifier = Modifier,
    entries: List<BarEntry>,
    maxValue: Float,
    midLineValue: Float,
    highlightIndex: Int,
    onBarSelected: (index: Int) -> Unit
) {
    Surface(modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)), color = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
//                Text(text = "Biểu đồ cân nặng\n(sử dụng thư viện vẽ biểu đồ)", textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                BarChart(
                    entries = entries,
                    maxValue = maxValue,
                    midLineValue = midLineValue,
                    highlightIndex = highlightIndex,
                    onBarSelected = onBarSelected
                )
            }
        }
    }
}
