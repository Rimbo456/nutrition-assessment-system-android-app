package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(

): BaseViewModel<CameraIntent, CameraViewStates.CameraViewState, CameraViewStates.CameraViewModelState>(
    initState = CameraViewStates.CameraViewModelState()
) {
    override fun onTriggerIntent(intent: CameraIntent) {
        viewModelScope.launch {
            when (intent) {
                is CameraIntent.RequestCameraPermission -> {
                    viewModelState.update { it.copy(triggerPermissionRequest = true) }
                }
                is CameraIntent.ObservePermission -> {
                    viewModelState.update {
                        it.copy(
                            isPermissionGranted = intent.isGranted,
                            shouldShowRationale = intent.shouldShowRationale,
                            showSettingsHint = (!intent.isGranted && !intent.shouldShowRationale),
                        )
                    }
                }
                is CameraIntent.PermissionRequestConsumed -> {
                    viewModelState.update { it.copy(triggerPermissionRequest = false) }
                }
                is CameraIntent.OpenAppSettings -> {
                    viewModelState.update { it.copy(showSettingsHint = true) }
                }
                is CameraIntent.TakePhoto -> {}
            }
        }
    }
}