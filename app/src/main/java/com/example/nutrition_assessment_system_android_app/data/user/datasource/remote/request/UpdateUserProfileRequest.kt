package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request

data class UpdateUserProfileRequest(
    val gender: String,
    val age: Int,
    val weight: Float,
    val height: Int,
    val activityLevel: String,
    val goal: String,
    val targetWeight : Double,
    val weeklyRate : Double
)