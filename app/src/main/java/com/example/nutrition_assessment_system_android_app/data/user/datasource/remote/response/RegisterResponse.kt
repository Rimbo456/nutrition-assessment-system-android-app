package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.response

import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.UserDto

data class RegisterResponse(
    val success: Boolean,
    val user: UserDto
)
