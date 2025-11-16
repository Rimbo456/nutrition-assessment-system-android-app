package com.example.nutrition_assessment_system_android_app.data.common.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutrition_assessment_system_android_app.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.get

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PreferencesRepository {
    private val Context.dataStore by preferencesDataStore(name = "app_preferences")
    private val datastore = context.dataStore

    override val languageFlow: Flow<String?> = datastore.data.map { preferences ->
        preferences[PreferencesKeys.APP_LANGUAGE]
    }

    override suspend fun updateLanguage(language: String) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.APP_LANGUAGE] = language
        }
    }

    override fun getAuthToken(): Flow<String?> {
        return datastore.data.map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }
    }

    override suspend fun updateAuthToken(token: String?) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token ?: ""
        }
    }

    override suspend fun clearAuthToken() {
        datastore.edit { preferences ->
            preferences.remove(PreferencesKeys.AUTH_TOKEN)
        }
    }
}