package com.example.nutrition_assessment_system_android_app.ui.feature.camera.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.div

/**
 * FocusCornerIndicator vẽ góc chữ L cho camera / scanner.
 * Các nét bo tròn hoàn toàn, các góc được bo mượt.
 *
 * @param modifier Modifier cho Compose.
 * @param color màu nét.
 * @param alpha độ trong suốt.
 * @param size kích thước tổng của canvas.
 * @param strokeWidth độ dày nét.
 * @param corner vị trí góc muốn vẽ.
 */
@Composable
fun FocusCornerIndicator(
    modifier: Modifier = Modifier,
    color: Color,
    alpha: Float = 1f,
    size: Dp = 32.dp,
    strokeWidth: Dp = 4.dp,
    corner: Corner
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val stroke = strokeWidth.toPx()

        // chừa viền để stroke không bị cắt
        val padding = stroke / 2f

        // độ dài mỗi nhánh của chữ L (tương đối theo kích thước)
        val horizontalLength = w * 0.45f
        val verticalLength = h * 0.45f

        // bán kính bo góc (có thể chỉnh tăng/giảm cho "mềm" hơn)
        val cornerRadius = minOf(horizontalLength, verticalLength) * 0.5f

        val path = Path()

        when (corner) {
            Corner.TopLeft -> {
                // Bắt đầu từ trái sang phải (ngang)
                path.moveTo(padding, padding)
                path.lineTo(padding + horizontalLength - cornerRadius, padding)
                // Bo xuống dưới
                path.quadraticBezierTo(
                    padding + horizontalLength,
                    padding,
                    padding + horizontalLength,
                    padding + cornerRadius
                )
                // Nét dọc xuống
                path.lineTo(padding + horizontalLength, padding + verticalLength)
            }

            Corner.TopRight -> {
                // Bắt đầu từ phải sang trái (ngang)
                path.moveTo(w - padding, padding)
                path.lineTo(w - padding - horizontalLength + cornerRadius, padding)
                // Bo xuống dưới
                path.quadraticBezierTo(
                    w - padding - horizontalLength,
                    padding,
                    w - padding - horizontalLength,
                    padding + cornerRadius
                )
                // Nét dọc xuống
                path.lineTo(w - padding - horizontalLength, padding + verticalLength)
            }

            Corner.BottomLeft -> {
                // Bắt đầu từ trái sang phải (ngang, phía dưới)
                path.moveTo(padding, h - padding)
                path.lineTo(padding + horizontalLength - cornerRadius, h - padding)
                // Bo lên trên
                path.quadraticBezierTo(
                    padding + horizontalLength,
                    h - padding,
                    padding + horizontalLength,
                    h - padding - cornerRadius
                )
                // Nét dọc lên
                path.lineTo(padding + horizontalLength, h - padding - verticalLength)
            }

            Corner.BottomRight -> {
                // Bắt đầu từ phải sang trái (ngang, phía dưới)
                path.moveTo(w - padding, h - padding)
                path.lineTo(w - padding - horizontalLength + cornerRadius, h - padding)
                // Bo lên trên
                path.quadraticBezierTo(
                    w - padding - horizontalLength,
                    h - padding,
                    w - padding - horizontalLength,
                    h - padding - cornerRadius
                )
                // Nét dọc lên
                path.lineTo(w - padding - horizontalLength, h - padding - verticalLength)
            }
        }

        drawPath(
            path = path,
            color = color.copy(alpha = alpha),
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )
    }
}


/**
 * Enum xác định góc muốn vẽ
 */
enum class Corner {
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight
}
