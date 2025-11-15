package com.example.nutrition_assessment_system_android_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserSettingRepository {
    val languageFlow: Flow<String?>
    suspend fun updateLanguage(language: String)
}