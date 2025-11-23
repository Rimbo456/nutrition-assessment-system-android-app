package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep

/**
 * All possible user intents in onboarding flow following MVI pattern
 */
sealed interface OnBoardingIntent: ViewIntent {
    // Data input intents
    data class SetGender(val gender: String): OnBoardingIntent
    data class SetAge(val age: Int): OnBoardingIntent
    data class SetHeight(val height: Int): OnBoardingIntent
    data class SetWeight(val weight: Float): OnBoardingIntent
    data class SetActivityLevel(val activityLevel: String): OnBoardingIntent
    data class SetGoal(val goal: String): OnBoardingIntent

    data object NavigateBack: OnBoardingIntent
    data object NavigateNext: OnBoardingIntent
}