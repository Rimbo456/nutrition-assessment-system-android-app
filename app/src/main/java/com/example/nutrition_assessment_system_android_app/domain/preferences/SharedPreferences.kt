package com.example.nutrition_assessment_system_android_app.domain.preferences

import com.example.nutrition_assessment_system_android_app.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    fun getAuthToken(): Flow<String?> {
        return preferencesRepository.getAuthToken()
    }

    suspend fun updateAuthToken(token: String) {
        preferencesRepository.updateAuthToken(token)
    }

    suspend fun clearAuthToken() {
        preferencesRepository.clearAuthToken()
    }
}