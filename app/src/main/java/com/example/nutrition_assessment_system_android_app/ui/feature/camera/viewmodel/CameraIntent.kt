package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface CameraIntent : ViewIntent {
    object RequestCameraPermission : CameraIntent
    data class ObservePermission(val isGranted: Boolean, val shouldShowRationale: Boolean) : CameraIntent
    object PermissionRequestConsumed : CameraIntent
    object OpenAppSettings : CameraIntent
}