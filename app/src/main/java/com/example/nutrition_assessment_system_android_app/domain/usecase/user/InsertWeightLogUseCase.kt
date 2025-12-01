package com.example.nutrition_assessment_system_android_app.domain.usecase.user

import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class InsertWeightLogUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Float, Resource<Unit>>() {
    override suspend fun execute(param: Float): Resource<Unit> {
        return userRepository.insertWeightLog(param)
    }
}