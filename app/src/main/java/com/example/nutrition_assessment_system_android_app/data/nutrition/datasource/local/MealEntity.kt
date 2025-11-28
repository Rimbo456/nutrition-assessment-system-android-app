package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.local

import androidx.room.Entity
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.NutritionItem
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.TotalNutrition

@Entity(tableName = "meals", primaryKeys = ["id"])
data class MealEntity(
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