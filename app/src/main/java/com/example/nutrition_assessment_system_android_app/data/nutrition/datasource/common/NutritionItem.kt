package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common

import com.google.gson.annotations.SerializedName

data class NutritionItem(
    @SerializedName("weight_g") val weightG: Double,
    val calories: Double,
    @SerializedName("protein_g") val proteinG: Double,
    @SerializedName("fat_g") val fatG: Double,
    @SerializedName("carbs_g") val carbsG: Double
)