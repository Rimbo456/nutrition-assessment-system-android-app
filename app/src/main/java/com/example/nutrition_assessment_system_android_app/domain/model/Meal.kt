package com.example.nutrition_assessment_system_android_app.domain.model

data class Meal(
    val id: String,
    val userId: String,
    val dishLabel: String,
    val servingSize: Double,
    val components: Map<String, NutritionItem>,
    val totalNutrition: TotalNutrition,
    val date: String,
    val type: Int,
    val createAt: String,
)