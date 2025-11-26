package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import androidx.camera.core.ImageCapture
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

sealed class CameraViewStates {
    data class CameraViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isPermissionGranted: Boolean = false,
        val shouldShowRationale: Boolean = false,
        val triggerPermissionRequest: Boolean = false,
        val showSettingsHint: Boolean = false,
        val imageCapture: ImageCapture? = null,
        val dish: String? = null,
    ): ViewState()

    data class CameraViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isPermissionGranted: Boolean = false,
        val shouldShowRationale: Boolean = false,
        val triggerPermissionRequest: Boolean = false,
        val showSettingsHint: Boolean = false,
        val imageCapture: ImageCapture? = null,
        val dish: String? = null,
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return CameraViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                isPermissionGranted = isPermissionGranted,
                shouldShowRationale = shouldShowRationale,
                triggerPermissionRequest = triggerPermissionRequest,
                showSettingsHint = showSettingsHint,
                imageCapture = imageCapture,
                dish = dish,
            )
        }
    }
}