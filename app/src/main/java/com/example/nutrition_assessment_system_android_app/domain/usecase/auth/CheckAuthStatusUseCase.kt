package com.example.nutrition_assessment_system_android_app.domain.usecase.auth

import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckAuthStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            val result = userRepository.checkAuthStatus()
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}