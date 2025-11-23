package com.example.nutrition_assessment_system_android_app.ui.feature.camera.screen

import androidx.camera.core.Camera
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.blur
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.R
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPermissionWrapper
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPreview
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraViewModel
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
private fun ReticleCorner(
    modifier: Modifier,
    color: Color,
    size: Dp = 50.dp,
    stroke: Dp = 4.dp,
    topLeft: Boolean = false,
    topRight: Boolean = false,
    bottomLeft: Boolean = false,
    bottomRight: Boolean = false
) {
    Canvas(modifier = modifier.size(size)) {
        val s = size.toPx()
        val l = s * 0.6f
        val strokeWidth = stroke.toPx()
        // Draw L shape with rounded corners
        drawPath(androidx.compose.ui.graphics.Path().apply {
            if (topLeft) {
                moveTo(0f, l); lineTo(0f, 0f); lineTo(l, 0f)
            } else if (topRight) {
                moveTo(s-l, 0f); lineTo(s, 0f); lineTo(s, l)
            } else if (bottomLeft) {
                moveTo(0f, s-l); lineTo(0f, s); lineTo(l, s)
            } else if (bottomRight) {
                moveTo(s-l, s); lineTo(s, s); lineTo(s, s-l)
            }
        }, color = color, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
    }
}

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onClose: () -> Unit = {},
    onMore: () -> Unit = {},
    onThumbnailClick: () -> Unit = {},
) {
    var camera by remember { mutableStateOf<Camera?>(null) }
    var torchEnabled by remember { mutableStateOf(false) }

    val accent = Color(0xFFB7FF3B) // green accent from mockup
    val accentGlow = Color(0xFF9FE629) // slightly darker for depth
    val bgFadeTop = Brush.verticalGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.6f),
            Color.Transparent
        ),
        startY = 0f,
        endY = 300f
    )
    val bgFadeBottom = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.7f)
        )
    )

    CameraPermissionWrapper(
        content = {
            CameraPreview(onCameraReady = { camera = it })

            Box(modifier = Modifier.fillMaxSize()) {
                // Top gradient overlay for better button visibility
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.TopCenter)
                        .background(bgFadeTop)
                )

                // Reticle corners with better positioning
                ReticleCorner(modifier = Modifier.align(Alignment.TopStart).padding(40.dp), color = accent, topLeft = true)
                ReticleCorner(modifier = Modifier.align(Alignment.TopEnd).padding(40.dp), color = accent, topRight = true)
                ReticleCorner(modifier = Modifier.align(Alignment.BottomStart).padding(40.dp), color = accent, bottomLeft = true)
                ReticleCorner(modifier = Modifier.align(Alignment.BottomEnd).padding(40.dp), color = accent, bottomRight = true)

                // Top buttons row
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.92f))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomIconButton(icon = Lucide.X, onClick = onClose, iconColors = Color.Black, sizeIcon = 24.dp)
                    }
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(if (torchEnabled) accent.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.92f))
                            .border(1.dp, if (torchEnabled) accent else Color.White.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomIconButton(
                            icon = if (torchEnabled) Lucide.FlashlightOff else Lucide.Flashlight,
                            onClick = {
                                torchEnabled = !torchEnabled
                                camera?.cameraControl?.enableTorch(torchEnabled)
                            },
                            iconColors = Color.Black,
                            sizeIcon = 24.dp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.92f))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomIconButton(icon = Lucide.EllipsisVertical, onClick = onMore, iconColors = Color.Black, sizeIcon = 24.dp)
                    }
                }

                // Bottom bar with improved design
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(bgFadeBottom)
                        .padding(horizontal = 28.dp, vertical = 32.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail with better styling
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                .clickable { onThumbnailClick() }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Last photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Capture button with glow effect
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .shadow(12.dp, CircleShape, spotColor = accent.copy(alpha = 0.5f))
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(accent, accentGlow),
                                        radius = 200f
                                    )
                                )
                                .border(3.dp, Color.White.copy(alpha = 0.4f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomIconButton(
                                icon = Lucide.Search,
                                onClick = { /* capture */ },
                                iconColors = Color.Black.copy(alpha = 0.85f),
                                sizeIcon = 36.dp
                            )
                        }

                        // Info button with better styling
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.95f))
                                .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomIconButton(
                                icon = Lucide.CircleHelp,
                                onClick = { /* info */ },
                                iconColors = Color.Black,
                                sizeIcon = 28.dp
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CameraScreenPreview() { }
