package com.example.nutrition_assessment_system_android_app.domain.repository

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.model.WeightLog
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
//    fun getAllUsers(forceRefresh: Boolean = false): Flow<Resource<List<User>>>

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Resource<User>

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User>

    suspend fun loginWithGoogle(
        googleToken: String
    ): Resource<User>

    suspend fun checkAuthStatus(): Resource<Boolean>
    fun observeAuthState(): Flow<Boolean>

    suspend fun getCurrentUser(): Resource<User>

    suspend fun updateUserProfile(
        gender: String,
        age: Int,
        height: Int,
        weight: Float,
        activityLevel: String,
        goal: String,
        targetWeight : Double,
        weeklyRate : Double
    ): Resource<User>

    suspend fun insertWeightLog(weight: Float): Resource<Unit>
    fun getWeightLogs(forceRefresh: Boolean): Flow<Resource<List<WeightLog>>>
}