package com.example.nutrition_assessment_system_android_app.ui.feature.camera.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraViewModel
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel.CameraIntent
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionWrapper(
    content: @Composable () -> Unit,
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val permissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Observe system permission each recomposition
    LaunchedEffect(permissionState.status) {
        viewModel.onTriggerIntent(
            CameraIntent.ObservePermission(
                isGranted = permissionState.status.isGranted,
                shouldShowRationale = permissionState.status.shouldShowRationale
            )
        )
    }

    // Fire actual permission request if flagged by state
    LaunchedEffect(uiState.triggerPermissionRequest) {
        if (uiState.triggerPermissionRequest) {
            permissionState.launchPermissionRequest()
            viewModel.onTriggerIntent(CameraIntent.PermissionRequestConsumed)
        }
    }

    when {
        uiState.isPermissionGranted -> content()
        uiState.shouldShowRationale -> RationaleBlock(onAccept = { viewModel.onTriggerIntent(CameraIntent.RequestCameraPermission) })
        uiState.showSettingsHint -> SettingsHintBlock(onOpenSettings = { viewModel.onTriggerIntent(CameraIntent.OpenAppSettings) }, onRetry = { viewModel.onTriggerIntent(CameraIntent.RequestCameraPermission) })
        else -> InitialRequestBlock(onRequest = { viewModel.onTriggerIntent(CameraIntent.RequestCameraPermission) })
    }
}

@Composable
private fun RationaleBlock(onAccept: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Ứng dụng cần quyền Camera để chụp ảnh. Vui lòng cấp quyền để tiếp tục.")
        Button(onClick = onAccept) { Text("Cho phép") }
    }
}

@Composable
private fun InitialRequestBlock(onRequest: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Ứng dụng cần quyền Camera để chụp ảnh.")
        Button(onClick = onRequest) { Text("Yêu cầu quyền") }
    }
}

@Composable
private fun SettingsHintBlock(onOpenSettings: () -> Unit, onRetry: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Quyền đã bị từ chối vĩnh viễn. Mở phần Cài đặt để cấp lại hoặc thử lại.")
        Button(onClick = onOpenSettings) { Text("Mở Cài đặt") }
        Button(onClick = onRetry) { Text("Thử lại") }
    }
}