package com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel

import com.example.nutrition_assessment_system_android_app.domain.model.WeightLog
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.OneTimeEvent
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class ProfileViewStates {
    data class ProfileViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val name: String? = null,
        val avatar: String? = null,
        val currentWeight: Double? = null,
        val startWeight: Double? = null,
        val targetWeight: Double? = null,
        val targetWater: Double? = null,
        val targetCalories: Double? = null,
        val age: Int? = null,
        val targetProtein: Double? = null,
        val targetFat: Double? = null,
        val height: Double? = null,
        val weeklyRate: Double? = null,
        val goal: String? = null,
        val bmi: Double? = null,
        val navigateToProfile: OneTimeEvent<Boolean>? = null,
        val carbohydrateGoal: Double? = null,
        val weightLogs: List<WeightLog> = emptyList()
    ): ViewState()

    data class ProfileViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val name: String? = null,
        val avatar: String? = null,
        val currentWeight: Double? = null,
        val startWeight: Double? = null,
        val targetWeight: Double? = null,
        val targetWater: Double? = null,
        val age: Int? = null,
        val targetProtein: Double? = null,
        val targetFat: Double? = null,
        val height: Double? = null,
        val weeklyRate: Double? = null,
        val targetCalories: Double? = null,
        val goal: String? = null,
        val bmi: Double? = null,
        val navigateToProfile: OneTimeEvent<Boolean>? = null,
        val carbohydrateGoal: Double? = null,
        val weightLogs: List<WeightLog> = emptyList()
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return ProfileViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                name = name,
                avatar = avatar,
                currentWeight = currentWeight,
                targetWeight = targetWeight,
                targetWater = targetWater,
                targetCalories = targetCalories,
                goal = goal,
                navigateToProfile = navigateToProfile,
                startWeight = startWeight,
                bmi = bmi,
                carbohydrateGoal = carbohydrateGoal,
                weightLogs = weightLogs,
                targetProtein = targetProtein,
                targetFat = targetFat,
                height = height,
                weeklyRate = weeklyRate,
                age = age
            )
        }
    }
}