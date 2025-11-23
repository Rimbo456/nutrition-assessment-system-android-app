package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums

enum class OnBoardingStep(val route: String) {
    GENDER("onboarding/gender"),
    AGE("onboarding/age"),
    HEIGHT("onboarding/height"),
    WEIGHT("onboarding/weight"),
    ACTIVITY("onboarding/activity"),
    GOAL("onboarding/goal");

    fun next(): OnBoardingStep =
        entries.getOrNull(ordinal + 1) ?: this

    fun previous(): OnBoardingStep =
        entries.getOrNull(ordinal - 1) ?: this
}