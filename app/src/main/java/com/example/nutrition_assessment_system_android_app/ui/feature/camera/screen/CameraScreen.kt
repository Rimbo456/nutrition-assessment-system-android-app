package com.example.nutrition_assessment_system_android_app.ui.feature.camera.screen

import android.util.Log
import androidx.camera.core.Camera
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.R
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPermissionWrapper
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPreview
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.Corner
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.FocusCornerIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraViewModel

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onClose: () -> Unit = {},
    onMore: () -> Unit = {},
    onThumbnailClick: () -> Unit = {},
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var cameraState by remember { mutableStateOf<Camera?>(null) }
    var torchEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        Log.d("CameraScreen", "dish: ${uiState.dish}")
        Log.d("CameraScreen", "error: ${uiState.errorMessage}")
    }

    CameraPermissionWrapper(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraPreview(
                    onCameraReady = { camera, capture ->
                        cameraState = camera
                        viewModel.onTriggerIntent(CameraIntent.SetImageCapture(capture))
                    }
                )

                // Top gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Bottom gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                Column(modifier = Modifier.fillMaxSize()) {
                    // Top bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomIconButton(
                            onClick = onClose,
                            icon = Lucide.X,
                            contentDescription = null,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White.copy(alpha = 0.95f)
                            ),
                            iconColors = Color.Black,
                            sizeIcon = 20.dp,
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(8.dp, CircleShape)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CustomIconButton(
                                onClick = {
                                    torchEnabled = !torchEnabled
                                    cameraState?.cameraControl?.enableTorch(torchEnabled)
                                },
                                icon = if (torchEnabled) Lucide.FlashlightOff else Lucide.Flashlight,
                                contentDescription = null,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = if (torchEnabled)
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
                                    else
                                        Color.White.copy(alpha = 0.95f)
                                ),
                                iconColors = if (torchEnabled)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    Color.Black,
                                sizeIcon = 20.dp,
                                modifier = Modifier
                                    .size(44.dp)
                                    .shadow(8.dp, CircleShape)
                            )

                            CustomIconButton(
                                onClick = onMore,
                                icon = Lucide.EllipsisVertical,
                                contentDescription = null,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.White.copy(alpha = 0.95f)
                                ),
                                iconColors = Color.Black,
                                sizeIcon = 20.dp,
                                modifier = Modifier
                                    .size(44.dp)
                                    .shadow(8.dp, CircleShape)
                            )
                        }
                    }

                    // Center focus frame
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
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

                    // Bottom bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .shadow(12.dp, CircleShape)
                                .clip(CircleShape)
                                .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                .clickable { onThumbnailClick() }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Last photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Capture button
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .shadow(16.dp, CircleShape)
                        ) {
                            CustomIconButton(
                                icon = Lucide.Camera,
                                onClick = {
                                    viewModel.onTriggerIntent(CameraIntent.TakePhoto(context))
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                iconColors = MaterialTheme.colorScheme.onPrimary,
                                sizeIcon = 32.dp,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(4.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                            )
                        }

                        // Spacer to balance layout
                        Box(modifier = Modifier.size(64.dp))
                    }
                }
            }
        }
    )
}

