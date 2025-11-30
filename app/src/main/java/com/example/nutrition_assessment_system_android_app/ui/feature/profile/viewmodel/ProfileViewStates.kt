package com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class ProfileViewStates {
    data class ProfileViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val name: String? = null,
        val avatar: String? = null,
        val currentWeight: Double? = null,
        val targetWeight: Double? = null,
        val targetWater: Double? = null,
        val targetCalories: Double? = null,
        val goal: String? = null,
    ): ViewState()

    data class ProfileViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val name: String? = null,
        val avatar: String? = null,
        val currentWeight: Double? = null,
        val targetWeight: Double? = null,
        val targetWater: Double? = null,
        val targetCalories: Double? = null,
        val goal: String? = null,
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
            )
        }
    }
}