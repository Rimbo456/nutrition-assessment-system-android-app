package com.example.nutrition_assessment_system_android_app.domain.params

data class UpdateUserProfileParams(
    val gender: String,
    val age: Int,
    val height: Int,
    val weight: Float,
    val activityLevel: String,
    val goal: String,
    val targetWeight: Double,
    val weeklyRate: Double,
)