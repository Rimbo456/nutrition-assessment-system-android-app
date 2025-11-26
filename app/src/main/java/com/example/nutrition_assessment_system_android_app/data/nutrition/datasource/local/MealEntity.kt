package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.local

import androidx.room.Entity

@Entity(tableName = "meals", primaryKeys = ["id"])
data class MealEntity(
    val id: String,
    val userId: String,
    val dishLabel: String,
    val estimatedServingSize: Float,
    val components: List<FoodEntity>,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val date: String,
)