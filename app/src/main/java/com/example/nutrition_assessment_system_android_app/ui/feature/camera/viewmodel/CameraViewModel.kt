package com.example.nutrition_assessment_system_android_app.ui.feature.camera.viewmodel

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition.AnalyzeImageUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition.SaveMealUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val analyzeImageUseCase: AnalyzeImageUseCase,
    private val saveMealUseCase: SaveMealUseCase
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
                is CameraIntent.ClearDish -> {
                    clearDish()
                }
                is CameraIntent.OnToggleEditing -> {
                    viewModelState.update {
                        it.copy(isEditing = !it.isEditing)
                    }
                }
                is CameraIntent.OnWeightChanged -> {
                    handleTotalWeightChange(intent.weight)
                }
                is CameraIntent.OnIngredientWeightChanged -> {
                    handleIngredientWeightChange(intent.ingredientId, intent.weight)
                }
                is CameraIntent.OnIngredientRemoved -> {
                    handleIngredientRemoved(intent.ingredientId)
                }
                is CameraIntent.SaveMeal -> {
                    val currentDish = viewModelState.value.dish ?: return@launch

                    viewModelState.update {
                        it.copy(isLoading = true)
                    }

                    saveMealUseCase.execute(currentDish).reduce(
                        onSuccess = {
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    dish = null,
                                    isEditing = false,
                                    errorMessage = null,
                                )
                            }
                        },
                        onError = { message, _ ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message,
                                )
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
        }
    }

    private fun setImageCapture(capture: ImageCapture) {
        viewModelState.update {
            it.copy(imageCapture = capture)
        }
    }

    private fun clearDish() {
        viewModelState.update {
            it.copy(dish = null, errorMessage = null)
        }
    }

    private fun handleTotalWeightChange(newWeight: Double) {
        val currentDish = viewModelState.value.dish
        if (currentDish != null) {
            val oldTotalWeight = currentDish.totalWeightG

            // Guard: nếu weight cũ <= 0 hoặc weight mới <= 0 thì chỉ update weight, tránh chia cho 0
            if (oldTotalWeight <= 0.0 || newWeight <= 0.0) {
                val updatedDish = currentDish.copy(totalWeightG = newWeight)
                viewModelState.update { it.copy(dish = updatedDish) }
            } else {
                val scale = newWeight / oldTotalWeight

                // Scale ingredients map
                val scaledIngredients = currentDish.ingredients.mapValues { (_, value) ->
                    value * scale
                }

                // Scale per-ingredient nutrition
                val scaledNutrition = currentDish.nutrition.mapValues { (_, item) ->
                    item.copy(
                        weightG = item.weightG * scale,
                        calories = item.calories * scale,
                        proteinG = item.proteinG * scale,
                        fatG = item.fatG * scale,
                        carbsG = item.carbsG * scale,
                    )
                }

                // Scale total nutrition
                val oldTotalNutrition = currentDish.totalNutrition
                val scaledTotalNutrition = oldTotalNutrition.copy(
                    calories = oldTotalNutrition.calories * scale,
                    proteinG = oldTotalNutrition.proteinG * scale,
                    fatG = oldTotalNutrition.fatG * scale,
                    carbsG = oldTotalNutrition.carbsG * scale,
                )

                val updatedDish = currentDish.copy(
                    totalWeightG = newWeight,
                    ingredients = scaledIngredients,
                    nutrition = scaledNutrition,
                    totalNutrition = scaledTotalNutrition,
                )

                viewModelState.update { it.copy(dish = updatedDish) }
            }
        }
    }

    private fun takePhoto(context: Context) {
        viewModelState.update {
            it.copy(
                isLoading = true
            )
        }

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
                                    it.copy(dish = dish, isLoading = false)
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

    private fun handleIngredientWeightChange(ingredientId: String, newWeight: Double) {
        val currentDish = viewModelState.value.dish ?: return

        val oldWeight = currentDish.ingredients[ingredientId] ?: return
        if (oldWeight <= 0.0 || newWeight <= 0.0) {
            // Nếu dữ liệu không hợp lệ, chỉ update weight của ingredient và totalWeight,
            // không cố gắng scale nutrition để tránh chia cho 0.
            val updatedIngredients = currentDish.ingredients.toMutableMap().apply {
                this[ingredientId] = newWeight
            }
            val updatedTotalWeight = updatedIngredients.values.sum()
            val updatedDish = currentDish.copy(
                ingredients = updatedIngredients,
                totalWeightG = updatedTotalWeight
            )
            viewModelState.update { it.copy(dish = updatedDish) }
            return
        }

        val factor = newWeight / oldWeight

        // Cập nhật weight cho ingredient
        val updatedIngredients = currentDish.ingredients.toMutableMap().apply {
            this[ingredientId] = newWeight
        }

        // Scale nutrition cho ingredient đó
        val oldItem = currentDish.nutrition[ingredientId]
        val updatedNutritionMap = currentDish.nutrition.toMutableMap().apply {
            if (oldItem != null) {
                this[ingredientId] = oldItem.copy(
                    weightG = oldItem.weightG * factor,
                    calories = oldItem.calories * factor,
                    proteinG = oldItem.proteinG * factor,
                    fatG = oldItem.fatG * factor,
                    carbsG = oldItem.carbsG * factor,
                )
            }
        }

        // Recompute totalWeightG từ ingredients
        val newTotalWeight = updatedIngredients.values.sum()

        // Recompute totalNutrition từ toàn bộ nutrition map
        val newTotalNutrition = updatedNutritionMap.values.fold(currentDish.totalNutrition.copy(
            calories = 0.0,
            proteinG = 0.0,
            fatG = 0.0,
            carbsG = 0.0
        )) { acc, item ->
            acc.copy(
                calories = acc.calories + item.calories,
                proteinG = acc.proteinG + item.proteinG,
                fatG = acc.fatG + item.fatG,
                carbsG = acc.carbsG + item.carbsG,
            )
        }

        val updatedDish = currentDish.copy(
            ingredients = updatedIngredients,
            nutrition = updatedNutritionMap,
            totalWeightG = newTotalWeight,
            totalNutrition = newTotalNutrition,
        )

        viewModelState.update { it.copy(dish = updatedDish) }
    }

    private fun handleIngredientRemoved(ingredientId: String) {
        val currentDish = viewModelState.value.dish ?: return

        if (!currentDish.ingredients.containsKey(ingredientId)) return

        val updatedIngredients = currentDish.ingredients.toMutableMap().apply {
            remove(ingredientId)
        }
        val updatedNutritionMap = currentDish.nutrition.toMutableMap().apply {
            remove(ingredientId)
        }

        val newTotalWeight = updatedIngredients.values.sum()

        val newTotalNutrition = updatedNutritionMap.values.fold(currentDish.totalNutrition.copy(
            calories = 0.0,
            proteinG = 0.0,
            fatG = 0.0,
            carbsG = 0.0
        )) { acc, item ->
            acc.copy(
                calories = acc.calories + item.calories,
                proteinG = acc.proteinG + item.proteinG,
                fatG = acc.fatG + item.fatG,
                carbsG = acc.carbsG + item.carbsG,
            )
        }

        val updatedDish = currentDish.copy(
            ingredients = updatedIngredients,
            nutrition = updatedNutritionMap,
            totalWeightG = newTotalWeight,
            totalNutrition = newTotalNutrition,
        )

        viewModelState.update { it.copy(dish = updatedDish) }
    }
}