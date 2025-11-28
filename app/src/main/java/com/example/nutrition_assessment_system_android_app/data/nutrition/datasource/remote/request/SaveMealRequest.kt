package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.request

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.NutritionItem
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.TotalNutrition
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.DishData

data class SaveMealRequest(
    val dish: DishData,
    val type: Int?
)