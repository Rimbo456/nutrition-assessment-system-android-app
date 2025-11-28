package com.example.nutrition_assessment_system_android_app.domain.repository

import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.model.Meal
import com.example.nutrition_assessment_system_android_app.domain.model.NutritionItem
import com.example.nutrition_assessment_system_android_app.domain.model.TotalNutrition
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import java.io.File

interface NutritionRepository {
    suspend fun analyzePhoto(image: File): Resource<Dish>

    suspend fun saveMeal(dish: Dish, type: Int?): Resource<Meal>
}