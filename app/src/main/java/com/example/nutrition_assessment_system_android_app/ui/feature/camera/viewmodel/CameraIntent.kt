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
    object ClearDish : CameraIntent
    object OnToggleEditing : CameraIntent
    data class OnWeightChanged(val weight: Double) : CameraIntent
    data class OnIngredientWeightChanged(val ingredientId: String, val weight: Double) : CameraIntent
    data class OnIngredientRemoved(val ingredientId: String) : CameraIntent
    object SaveMeal : CameraIntent
}