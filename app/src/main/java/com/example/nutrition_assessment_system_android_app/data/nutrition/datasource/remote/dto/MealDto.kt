package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.dto

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.NutritionItem
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.TotalNutrition

data class MealDto(
    val id: String,
    val userId: String,
    val dishLabel: String,
    val servingSize: Float,
    val components: Map<String, NutritionItem>,
    val totalNutrition: TotalNutrition,
    val date: String,
    val type: Int,
    val createAt: String,
)