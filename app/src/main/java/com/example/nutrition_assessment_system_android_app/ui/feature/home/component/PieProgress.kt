package com.example.nutrition_assessment_system_android_app.ui.feature.home.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun PieProgress(
    progress: Int,
    sum: Int,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 12.dp,
    backgroundColor: Color = Color(0xFFE0E0E0),
    progressColor: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = 800,
    animationDelay: Int = 0
) {
    val target = (progress.toFloat() / sum.toFloat()).coerceIn(0f, 1f)
    val animated = animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay,
            easing = LinearOutSlowInEasing
        )
    ).value

    val animatedProgress = (animated * sum).roundToInt()

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
                .padding(strokeWidth / 2)
        ) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * animated,
                useCenter = false,
                style = stroke
            )
        }

        Box(
            modifier = Modifier
                .size(size - (strokeWidth + 5.dp))
                .clip(shape = RoundedCornerShape(100))
                .background(color = Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size((size - (strokeWidth + 5.dp)) - 20.dp)
                    .clip(shape = RoundedCornerShape(100))
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$animatedProgress",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(1.dp)
                    )
                    Text(
                        text = "$sum",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PieProgressPreview() {
    PieProgress(
        progress = 1250,
        sum = 1850
    )
}