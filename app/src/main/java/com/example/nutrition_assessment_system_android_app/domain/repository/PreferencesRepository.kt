package com.example.nutrition_assessment_system_android_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val languageFlow: Flow<String?>
    suspend fun updateLanguage(language: String)

    fun getAuthToken(): Flow<String?>
    suspend fun updateAuthToken(token: String?)
    suspend fun clearAuthToken()
}