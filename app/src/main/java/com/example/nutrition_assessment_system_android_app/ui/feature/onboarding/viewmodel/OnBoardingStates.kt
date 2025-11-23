package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep

/**
 * Onboarding states following MVI pattern
 */
class OnBoardingStates {
    data class OnBoardingViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,

        // Current step in onboarding flow
        val currentStep: OnBoardingStep = OnBoardingStep.GENDER,

        // User data
        val gender: String? = null,
        val age: Int? = null,
        val height: Int? = null,
        val weight: Float? = null,
        val activityLevel: String? = null,
        val goal: String? = null,

        // Validation states
        val isGenderValid: Boolean = false,
        val isAgeValid: Boolean = false,
        val isHeightValid: Boolean = false,
        val isWeightValid: Boolean = false,
        val isActivityLevelValid: Boolean = false,
        val isGoalValid: Boolean = false,
    ): ViewState()

    data class OnBoardingViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,

        // Current step in onboarding flow
        val currentStep: OnBoardingStep = OnBoardingStep.GENDER,

        // User data
        val gender: String? = null,
        val age: Int? = null,
        val height: Int? = null,
        val weight: Float? = null,
        val activityLevel: String? = null,
        val goal: String? = null,

        // Validation states
        val isGenderValid: Boolean = false,
        val isAgeValid: Boolean = false,
        val isHeightValid: Boolean = false,
        val isWeightValid: Boolean = false,
        val isActivityLevelValid: Boolean = false,
        val isGoalValid: Boolean = false,
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return OnBoardingViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                currentStep = currentStep,
                gender = gender,
                age = age,
                height = height,
                weight = weight,
                activityLevel = activityLevel,
                goal = goal,
                isGenderValid = isGenderValid,
                isAgeValid = isAgeValid,
                isHeightValid = isHeightValid,
                isWeightValid = isWeightValid,
                isActivityLevelValid = isActivityLevelValid,
                isGoalValid = isGoalValid,
            )
        }
    }
}