package com.example.nutrition_assessment_system_android_app.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String?,
    val gender: String?,
    val age: Int?,
    val weight: Float?,
    val height: Float?,
    val activityLevel: String?,
    val goal: String?,
    val preferences: List<String>?,
    val createAt: String,
)