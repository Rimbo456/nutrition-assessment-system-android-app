package com.example.nutrition_assessment_system_android_app.ui.feature.camera.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CenterFocusFrame(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Animated corner indicators
        val infiniteTransition = rememberInfiniteTransition(label = "corner_blink")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha_animation"
        )

        Box(modifier = Modifier.size(width = 300.dp, height = 450.dp)) {
            // Corner indicators với nét vẽ tròn hoàn toàn
            FocusCornerIndicator(
                modifier = Modifier.align(Alignment.TopStart),
                color = MaterialTheme.colorScheme.primary,
                alpha = alpha,
                size = 40.dp,
                strokeWidth = 4.dp,
                corner = Corner.TopRight
            )

            FocusCornerIndicator(
                modifier = Modifier.align(Alignment.TopEnd),
                color = MaterialTheme.colorScheme.primary,
                alpha = alpha,
                size = 40.dp,
                strokeWidth = 4.dp,
                corner = Corner.TopLeft
            )

            FocusCornerIndicator(
                modifier = Modifier.align(Alignment.BottomStart),
                color = MaterialTheme.colorScheme.primary,
                alpha = alpha,
                size = 40.dp,
                strokeWidth = 4.dp,
                corner = Corner.BottomRight
            )

            FocusCornerIndicator(
                modifier = Modifier.align(Alignment.BottomEnd),
                color = MaterialTheme.colorScheme.primary,
                alpha = alpha,
                size = 40.dp,
                strokeWidth = 4.dp,
                corner = Corner.BottomLeft
            )
        }
    }
}