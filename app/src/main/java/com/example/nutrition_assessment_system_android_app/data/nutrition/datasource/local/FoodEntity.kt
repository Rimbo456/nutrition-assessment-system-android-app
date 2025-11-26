package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.local

import androidx.room.Entity

@Entity(tableName = "foods", primaryKeys = ["id"])
data class FoodEntity(
    val id: String,
    val name: String,
    val calories: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrates: Float,
    val fiber: Float,
    val sugar: Float,
    val servingSize: String,
)