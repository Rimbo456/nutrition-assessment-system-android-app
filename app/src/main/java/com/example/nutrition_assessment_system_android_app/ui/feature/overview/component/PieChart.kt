package com.example.nutrition_assessment_system_android_app.ui.feature.overview.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// Data cho từng lát trong pie chart
data class PieSlice(
    val value: Float,
    val color: Color,
)

@Composable
fun PieChart(
    data: List<PieSlice>,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
) {
    val cleaned = data.filter { it.value > 0f }
    if (cleaned.isEmpty()) return

    val total = cleaned.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)

    // animation khi load chart
    val startAnimation = remember { mutableStateOf(false) }
    val overallSweep by animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "pie_overall_sweep"
    )

    LaunchedEffect(Unit) {
        startAnimation.value = true
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val radius = min(this.size.width, this.size.height) / 2f
            val center = this.center
            val availableAngle = 360f

            var startAngle = -90f

            cleaned.forEachIndexed { index, slice ->
                val percent = slice.value / total
                val sweep = percent * availableAngle * overallSweep

                if (sweep <= 0f) return@forEachIndexed

                val midAngle = startAngle + sweep / 2f
                val rad = midAngle * PI / 180f

                // vẽ slice
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true,
                    style = Fill,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )

                // vẽ text phần trăm
                val textRadius = radius * 0.55f
                val textX = center.x + (cos(rad) * textRadius).toFloat()
                val textY = center.y + (sin(rad) * textRadius).toFloat()

                val text = "${"%.0f".format(percent * 100)}%"

                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        color = android.graphics.Color.argb(255, 255, 85, 85)
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 16.sp.toPx()
                        typeface = android.graphics.Typeface.create(
                            android.graphics.Typeface.DEFAULT,
                            android.graphics.Typeface.BOLD
                        )
                    }
                    canvas.nativeCanvas.drawText(
                        text,
                        textX,
                        textY + paint.textSize / 3f,
                        paint
                    )
                }

                startAngle += sweep
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    val data = listOf(
        PieSlice(55f, Color(0xFFFFC0CB)), // hồng
        PieSlice(25f, Color(0xFFFFF1A8)), // vàng nhạt
        PieSlice(20f, Color(0xFFB8E2FF)), // xanh dương nhạt
    )

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6)),
            contentAlignment = Alignment.Center
        ) {
            PieChart(data = data, size = 220.dp)
        }
    }
}
