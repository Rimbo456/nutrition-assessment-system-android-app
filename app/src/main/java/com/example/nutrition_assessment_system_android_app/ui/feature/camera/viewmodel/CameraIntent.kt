package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import android.content.Context
import androidx.camera.core.ImageCapture
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent
import java.io.File

sealed interface CameraIntent : ViewIntent {
    object RequestCameraPermission : CameraIntent
    data class ObservePermission(val isGranted: Boolean, val shouldShowRationale: Boolean) : CameraIntent
    object PermissionRequestConsumed : CameraIntent
    object OpenAppSettings : CameraIntent
    data class SetImageCapture(val capture: ImageCapture) : CameraIntent
    data class TakePhoto(val context: Context): CameraIntent
}