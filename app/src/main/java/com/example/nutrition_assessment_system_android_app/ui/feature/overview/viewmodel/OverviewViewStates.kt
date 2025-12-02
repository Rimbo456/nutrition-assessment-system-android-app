package com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel

import com.example.nutrition_assessment_system_android_app.domain.model.Meal
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.FoodItem

class OverviewViewStates {
    data class OverviewViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val caloriesTarget: Double? = null,
        val proteinTarget: Double? = null,
        val fatTarget: Double? = null,
        val carbTarget: Double? = null,
        val waterTarget: Double? = null,
        val meals: List<Meal> = emptyList(),
        val totalCalories: Int? = null,
        val totalProtein: Int? = null,
        val totalFat: Int? = null,
        val totalCarb: Int? = null,
        val totalWater: Double? = null,
        val breakfastItems: List<FoodItem> = emptyList(),
        val lunchItems: List<FoodItem> = emptyList(),
        val dinnerItems: List<FoodItem> = emptyList(),
        val snackItems: List<FoodItem> = emptyList(),
    ): ViewState()

    data class OverviewViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val caloriesTarget: Double? = null,
        val proteinTarget: Double? = null,
        val fatTarget: Double? = null,
        val carbTarget: Double? = null,
        val waterTarget: Double? = null,
        val meals: List<Meal> = emptyList(),
        val totalCalories: Int? = null,
        val totalProtein: Int? = null,
        val totalFat: Int? = null,
        val totalCarb: Int? = null,
        val totalWater: Double? = null,
        val breakfastItems: List<FoodItem> = emptyList(),
        val lunchItems: List<FoodItem> = emptyList(),
        val dinnerItems: List<FoodItem> = emptyList(),
        val snackItems: List<FoodItem> = emptyList(),
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return OverviewViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                caloriesTarget = caloriesTarget,
                proteinTarget = proteinTarget,
                fatTarget = fatTarget,
                carbTarget = carbTarget,
                waterTarget = waterTarget,
                meals = meals,
                totalCalories = totalCalories,
                totalProtein = totalProtein,
                totalFat = totalFat,
                totalCarb = totalCarb,
                totalWater = totalWater,
                breakfastItems = breakfastItems,
                lunchItems = lunchItems,
                dinnerItems = dinnerItems,
                snackItems = snackItems,
            )
        }
    }
}