package com.example.nutrition_assessment_system_android_app.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String?,
    val gender: String?,
    val age: Int?,
    val weight: Float?,
    val height: Int?,
    val activityLevel: String?,
    val goal: String?,
    val targetWeight: Double? = null,
    val weeklyRate: Double? = null,
    val preferences: Preferences?,
    val createAt: String,
)