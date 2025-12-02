package com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition.GetMealsByDateUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.BasicCalculationUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val basicCalculationUseCase: BasicCalculationUseCase,
    private val getMealsByDateUseCase: GetMealsByDateUseCase
) : BaseViewModel<OverviewIntent, OverviewViewStates.OverviewViewState, OverviewViewStates.OverviewViewModelState>(
    initState = OverviewViewStates.OverviewViewModelState()
) {
    override fun onTriggerIntent(intent: OverviewIntent) {
        viewModelScope.launch {
            when (intent) {
                is OverviewIntent.GetBasicInfo -> basicCalculation()
                is OverviewIntent.GetMealsByDate -> handleGetMealsByDate(intent.date)
            }
        }
    }

    fun basicCalculation() {
        viewModelScope.launch {
            basicCalculationUseCase.execute(Unit).reduce(
                onSuccess = { data ->
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            caloriesTarget = data.caloriesTarget,
                            proteinTarget = data.proteinTarget,
                            fatTarget = data.fatTarget,
                            carbTarget = data.carbTarget,
                            waterTarget = data.waterTarget
                        )
                    }
                },
                onError = { message, error ->
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = message
                        )
                    }
                }
            )
        }
    }

    fun handleGetMealsByDate(date: String) {
        viewModelScope.launch {
            getMealsByDateUseCase.execute(date).reduce(
                onSuccess = { data ->
                    val breakfastMeals = data.filter { it.type == 0 }
                    val lunchMeals = data.filter { it.type == 1 }
                    val dinnerMeals = data.filter { it.type == 2 }
                    val snackMeals = data.filter { it.type == 3 }
                    val total = breakfastMeals + lunchMeals + dinnerMeals + snackMeals
                    val totalCalories = total.sumOf { it.totalNutrition.calories.toInt() }
                    val totalProtein = total.sumOf { it.totalNutrition.proteinG.toInt() }
                    val totalCarbs = total.sumOf { it.totalNutrition.carbsG.toInt() }
                    val totalFat = total.sumOf { it.totalNutrition.fatG.toInt() }
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            meals = data,
                            breakfastItems = breakfastMeals.map {
                                FoodItem(
                                    id = it.id,
                                    name = it.dishLabel,
                                    calories = it.totalNutrition.calories.toInt(),
                                    protein = it.totalNutrition.proteinG.toInt(),
                                    carbs = it.totalNutrition.carbsG.toInt(),
                                    fat = it.totalNutrition.fatG.toInt(),
                                    servingSize = it.servingSize.toString(),
                                    time = it.createAt
                                )
                            },
                            lunchItems = lunchMeals.map {
                                FoodItem(
                                    id = it.id,
                                    name = it.dishLabel,
                                    calories = it.totalNutrition.calories.toInt(),
                                    protein = it.totalNutrition.proteinG.toInt(),
                                    carbs = it.totalNutrition.carbsG.toInt(),
                                    fat = it.totalNutrition.fatG.toInt(),
                                    servingSize = it.servingSize.toString(),
                                    time = it.createAt
                                )
                            },
                            dinnerItems = dinnerMeals.map {
                                FoodItem(
                                    id = it.id,
                                    name = it.dishLabel,
                                    calories = it.totalNutrition.calories.toInt(),
                                    protein = it.totalNutrition.proteinG.toInt(),
                                    carbs = it.totalNutrition.carbsG.toInt(),
                                    fat = it.totalNutrition.fatG.toInt(),
                                    servingSize = it.servingSize.toString(),
                                    time = it.createAt
                                )
                            },
                            snackItems = snackMeals.map {
                                FoodItem(
                                    id = it.id,
                                    name = it.dishLabel,
                                    calories = it.totalNutrition.calories.toInt(),
                                    protein = it.totalNutrition.proteinG.toInt(),
                                    carbs = it.totalNutrition.carbsG.toInt(),
                                    fat = it.totalNutrition.fatG.toInt(),
                                    servingSize = it.servingSize.toString(),
                                    time = it.createAt
                                )
                            },
                            totalCalories = totalCalories,
                            totalProtein = totalProtein,
                            totalCarb = totalCarbs,
                            totalFat = totalFat
                        )
                    }
                },
                onError = { message, error ->
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = message
                        )
                    }
                },
                onLoading = {
                    viewModelState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            )
        }
    }
}