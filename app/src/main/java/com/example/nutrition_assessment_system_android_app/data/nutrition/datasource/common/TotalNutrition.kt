package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common

import com.google.gson.annotations.SerializedName

data class TotalNutrition(
    val calories: Double,
    @SerializedName("protein") val proteinG: Double,
    @SerializedName("fat") val fatG: Double,
    @SerializedName("carbohydrates") val carbsG: Double
)