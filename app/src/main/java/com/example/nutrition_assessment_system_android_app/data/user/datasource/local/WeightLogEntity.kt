package com.example.nutrition_assessment_system_android_app.data.user.datasource.local

import androidx.room.Entity

@Entity(tableName = "weight_logs", primaryKeys = ["id"])
data class WeightLogEntity(
    val id: String,
    val weight: Double,
    val timestamp: String,
    val dateString: String
)