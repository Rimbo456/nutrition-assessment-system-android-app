package com.example.nutrition_assessment_system_android_app.ui.component.bar

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ProgressBar(
    progress: Float, // 0f..1f
    modifier: Modifier = Modifier,
    height: Dp = 16.dp,
    backgroundColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = MaterialTheme.colorScheme.primary,
    showPercentage: Boolean = true,
    animationDuration: Int = 800,
    animationDelay: Int = 0
) {
    val target = progress.coerceIn(0f, 1f)
    val animated = animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = LinearOutSlowInEasing
        )
    ).value

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(height)
                .clip(RoundedCornerShape(height / 2))
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animated)
                    .clip(RoundedCornerShape(height / 2))
                    .background(progressColor)
            )
        }

        if (showPercentage) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(color = Color.Transparent) {
                Text(
                    text = "${(animated * 100f).roundToInt()}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProgressBar(progress = 0.25f)
        ProgressBar(progress = 0.5f, progressColor = Color(0xFF4CAF50))
        ProgressBar(progress = 0.75f, height = 24.dp, showPercentage = false)
        ProgressBar(progress = 1f, progressColor = Color(0xFFFF5722))
    }
}