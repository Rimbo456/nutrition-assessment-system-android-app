package com.example.nutrition_assessment_system_android_app.ui.feature.overview.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// Data cho từng cột
data class BarEntry(
    val value: Float,
    val dayLabel: String, // ví dụ: "25", "26", "27", ...
)

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    entries: List<BarEntry>,
    // maxValue dùng để scale chiều cao, nên truyền cùng giá trị với cột target (ví dụ 1850)
    maxValue: Float = 1850f,
    // giá trị nét đứt ở giữa (ví dụ 1000)
    midLineValue: Float = 1000f,
    // index của cột đang highlight (ví dụ hôm nay)
    highlightIndex: Int? = null,
    // callback khi người dùng bấm vào một cột
    onBarSelected: ((Int) -> Unit)? = null,
) {
    val density = LocalDensity.current

    // Tạo progress riêng cho từng cột để tránh gọi @Composable trong Canvas scope
    val barProgressList = remember(entries) {
        List(entries.size) { Animatable(0f) }
    }

    LaunchedEffect(entries) {
        coroutineScope {
            barProgressList.forEachIndexed { index, anim ->
                launch {
                    anim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 600, delayMillis = index * 80)
                    )
                }
            }
        }
    }

    val todayColor = Color(0xFFB1CC70)            // xanh lá cho cột đang chọn
    val barColor = Color(0xFFE1E1E1)              // xám cho các cột còn lại
    val axisTextColor = Color(0xFF8E8E8E)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(entries, maxValue, midLineValue) {
                    // Bắt sự kiện tap để xác định cột được bấm
                    detectTapGestures { offset: Offset ->
                        if (entries.isEmpty() || onBarSelected == null) return@detectTapGestures

                        val chartWidth = size.width - with(density) { 40.dp.toPx() }

                        val barWidth = chartWidth / (entries.size * 1.6f)
                        val gap = (chartWidth - barWidth * entries.size) / (entries.size - 1)

                        val tappedX = offset.x.coerceIn(0f, chartWidth)
                        val slotWidth = barWidth + gap
                        val index = (tappedX / slotWidth).toInt().coerceIn(0, entries.lastIndex)
                        onBarSelected(index)
                    }
                }
        ) {
            val chartHeight = size.height - with(density) { 24.dp.toPx() } // chừa chỗ cho label ngày
            val chartWidth = size.width - with(density) { 40.dp.toPx() }   // chừa chỗ cho số 0/1000/1850 bên phải

            val barWidth = chartWidth / (entries.size * 1.6f)
            val gap = (chartWidth - barWidth * entries.size) / (entries.size - 1)

            // --- Vẽ label 0 / mid / max bên phải ---
            drawYAxisLabel("0", 0f, maxValue, chartHeight, chartWidth, axisTextColor)
            drawYAxisLabel(midLineValue.toInt().toString(), midLineValue, maxValue, chartHeight, chartWidth, axisTextColor)
            drawYAxisLabel(maxValue.toInt().toString(), maxValue, maxValue, chartHeight, chartWidth, axisTextColor)

            // --- Vẽ line nét đứt ở midLineValue ---
            drawHorizontalDashLine(midLineValue, maxValue, chartHeight, chartWidth)

            // --- Vẽ vùng target ở trên cùng (giống dạng gạch chéo) ---
            drawTopTargetArea(maxValue, maxValue, chartHeight, chartWidth)

            // --- Vẽ từng cột ---
            entries.forEachIndexed { index, bar ->
                val progress = barProgressList.getOrNull(index)?.value ?: 1f

                val valueRatio = (bar.value / maxValue).coerceIn(0f, 1f)
                val barHeight = chartHeight * valueRatio * progress

                val x = index * (barWidth + gap)
                val top = chartHeight - barHeight

                // cột bo tròn
                drawRoundRect(
                    color = if (highlightIndex == index) todayColor else barColor,
                    topLeft = Offset(x, top),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
                )

                // label ngày ở dưới (25,26,27,..)
                drawDayLabel(bar.dayLabel, x + barWidth / 2f, chartHeight + with(density) { 16.dp.toPx() }, axisTextColor)
            }
        }
    }
}

// Vẽ số 0 / 1000 / 1850 ở bên phải
private fun DrawScope.drawYAxisLabel(
    text: String,
    value: Float,
    maxValue: Float,
    chartHeight: Float,
    chartWidth: Float,
    color: Color,
) {
    val ratio = (value / maxValue).coerceIn(0f, 1f)
    val y = chartHeight * (1f - ratio)

    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            this.color = android.graphics.Color.argb(255, (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt())
            textSize = 11.sp.toPx()
            textAlign = android.graphics.Paint.Align.RIGHT
        }
        canvas.nativeCanvas.drawText(
            text,
            chartWidth + 36.dp.toPx(),
            y + paint.textSize / 3f,
            paint
        )
    }
}

// line nét đứt ở giữa
private fun DrawScope.drawHorizontalDashLine(
    value: Float,
    maxValue: Float,
    chartHeight: Float,
    chartWidth: Float,
) {
    val ratio = (value / maxValue).coerceIn(0f, 1f)
    val y = chartHeight * (1f - ratio)

    drawLine(
        color = Color(0xFFE1E1E1),
        start = Offset(0f, y),
        end = Offset(chartWidth, y),
        strokeWidth = 1.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
    )
}

// vùng ngang trên cùng (target) – đơn giản hóa thành dải màu nhạt
private fun DrawScope.drawTopTargetArea(
    value: Float,
    maxValue: Float,
    chartHeight: Float,
    chartWidth: Float,
) {
    val ratio = (value / maxValue).coerceIn(0f, 1f)
    val y = chartHeight * (1f - ratio)

    drawRoundRect(
        color = Color(0xFFEDEDED),
        topLeft = Offset(0f, y - 6.dp.toPx()),
        size = Size(chartWidth, 10.dp.toPx()),
        cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
    )
}

// label ngày dưới trục X
private fun DrawScope.drawDayLabel(
    text: String,
    centerX: Float,
    baselineY: Float,
    color: Color,
) {
    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            this.color = android.graphics.Color.argb(255, (color.red * 255).toInt(), (color.green * 255).toInt(), (color.blue * 255).toInt())
            textSize = 11.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.nativeCanvas.drawText(text, centerX, baselineY, paint)
    }
}

@Preview(showBackground = true)
@Composable
fun BarChartPreview() {
    val entries = listOf(
        BarEntry(900f, "25"),
        BarEntry(1100f, "26"),
        BarEntry(700f, "27"),
        BarEntry(1600f, "28"),
        BarEntry(1850f, "29"),
        BarEntry(950f, "1"),
        BarEntry(1200f, "2"),
    )

    val selectedIndex = remember { mutableIntStateOf(entries.lastIndex) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6)),
            contentAlignment = Alignment.Center
        ) {
            BarChart(
                entries = entries,
                highlightIndex = selectedIndex.intValue,
                onBarSelected = { selectedIndex.intValue = it }
            )
        }
    }
}
