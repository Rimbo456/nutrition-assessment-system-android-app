package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition.AnalyzeImageUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val analyzeImageUseCase: AnalyzeImageUseCase
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
                is CameraIntent.TakePhoto -> {
                    takePhoto(intent.context)
                }
                is CameraIntent.SetImageCapture -> {
                    setImageCapture(intent.capture)
                }
            }
        }
    }

    private fun setImageCapture(capture: ImageCapture) {
        viewModelState.update {
            it.copy(imageCapture = capture)
        }
    }

    private fun takePhoto(context: Context) {
        val capture = viewModelState.value.imageCapture ?: return
        val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        capture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // execute suspend use case from a coroutine
                    viewModelScope.launch(Dispatchers.IO) {
                        analyzeImageUseCase.execute(file).reduce(
                            onSuccess = { dish ->
                                viewModelState.update {
                                    it.copy(dish = dish.name)
                                }
                            },
                            onError = { message, _ ->
                                viewModelState.update {
                                    it.copy(isLoading = false, errorMessage = message)
                                }
                            },
                            onLoading = {
                                viewModelState.update {
                                    it.copy(isLoading = true)
                                }
                            }
                        )
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )
    }
}