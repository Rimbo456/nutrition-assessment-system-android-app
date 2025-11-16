package com.example.nutrition_assessment_system_android_app.domain.usecase.auth

import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return userRepository.observeAuthState()
    }
}