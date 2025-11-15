package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto (
    val id: String,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val weight: Float? = null,
    val height: Float? = null,
    val activityLevel: String? = null,
    val goal: String? = null,
    val preferences: List<String>? = emptyList(),
    @SerializedName("createdAt")
    val createAt: String,
)