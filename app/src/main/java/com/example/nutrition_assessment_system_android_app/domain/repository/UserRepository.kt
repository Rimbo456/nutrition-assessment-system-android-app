package com.example.nutrition_assessment_system_android_app.domain.repository

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(forceRefresh: Boolean = false): Flow<Resource<List<User>>>
}