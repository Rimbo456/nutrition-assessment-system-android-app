package com.example.nutrition_assessment_system_android_app.domain.usecase.user

import com.example.nutrition_assessment_system_android_app.domain.model.WeightLog
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeightLogsUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<Boolean, Flow<Resource<List<WeightLog>>>>() {
    override suspend fun execute(param: Boolean): Flow<Resource<List<WeightLog>>> {
        return userRepository.getWeightLogs(param)
    }
}