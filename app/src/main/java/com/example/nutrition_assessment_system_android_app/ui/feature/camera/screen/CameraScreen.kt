package com.example.nutrition_assessment_system_android_app.ui.feature.camera.screen

import android.util.Log
import androidx.camera.core.Camera
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.NavController
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.R
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.BottomSheetContent
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPermissionWrapper
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CameraPreview
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.component.CenterFocusFrame
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    var showSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(uiState) {
        Log.d("CameraScreen", "dish: ${uiState.dish}")
        Log.d("CameraScreen", "error: ${uiState.errorMessage}")
        Log.d("CameraScreen", "loading: ${uiState.isLoading}")
        if (uiState.dish != null) {
            showSheet = true
        }
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
                    CenterFocusFrame(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

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
    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (showSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet = false
                viewModel.onTriggerIntent(CameraIntent.ClearDish)
            }
        ) {
            BottomSheetContent(
                dish = uiState.dish,
                isEditing = uiState.isEditing,
                onEditToggle = { viewModel.onTriggerIntent(CameraIntent.OnToggleEditing) },
                onWeightChange = { weight ->
                    viewModel.onTriggerIntent(CameraIntent.OnWeightChanged(weight))
                },
                onIngredientWeightChange = { ingredientId, weight ->
                    viewModel.onTriggerIntent(
                        CameraIntent.OnIngredientWeightChanged(
                            ingredientId,
                            weight
                        )
                    )
                },
                onIngredientRemove = { ingredientId ->
                    viewModel.onTriggerIntent(CameraIntent.OnIngredientRemoved(ingredientId))
                }
            )
        }
    }
}

