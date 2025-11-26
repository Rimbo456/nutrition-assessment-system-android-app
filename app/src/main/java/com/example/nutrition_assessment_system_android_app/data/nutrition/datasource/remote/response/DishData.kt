package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class DishData(
    val dish: String,
    @SerializedName("total_weight_g") val totalWeightG: Double,
    val ingredients: Map<String, Double>,
    val nutrition: Map<String, NutritionItem>,
    @SerializedName("total_nutrition") val totalNutrition: TotalNutrition
)