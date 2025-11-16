package com.example.nutrition_assessment_system_android_app.data.user.datasource.local

import androidx.room.Entity
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.PreferencesDto
import com.example.nutrition_assessment_system_android_app.domain.model.Preferences

@Entity(tableName = "users", primaryKeys = ["email"])
data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String?,
    val gender: String?,
    val age: Int?,
    val weight: Float?,
    val height: Float?,
    val activityLevel: String?,
    val goal: String?,
    val preferences: PreferencesDto?,
    val createAt: String,
)