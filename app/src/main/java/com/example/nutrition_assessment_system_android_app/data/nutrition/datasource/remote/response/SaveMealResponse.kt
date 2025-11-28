package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.dto.MealDto

data class SaveMealResponse(
    val success: Boolean,
    val data: MealDto
)