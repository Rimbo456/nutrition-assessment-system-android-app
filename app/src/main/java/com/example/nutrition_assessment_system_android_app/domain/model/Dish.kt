package com.example.nutrition_assessment_system_android_app.domain.model

data class Dish(
    val name: String,
    val totalWeightG: Double,
    val ingredients: Map<String, Double>,
    val nutrition: Map<String, NutritionItem>,
    val totalNutrition: TotalNutrition
)

data class NutritionItem(
    val weightG: Double,
    val calories: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbsG: Double
)

data class TotalNutrition(
    val calories: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbsG: Double
)