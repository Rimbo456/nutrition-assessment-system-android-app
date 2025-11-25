package com.example.nutrition_assessment_system_android_app.domain.usecase.user

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.params.UpdateUserProfileParams
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class UpdateProfileUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<UpdateUserProfileParams, Resource<User>>() {
    override suspend fun execute(param: UpdateUserProfileParams): Resource<User> {
        return userRepository.updateUserProfile(
            gender = param.gender,
            age = param.age,
            height = param.height,
            weight = param.weight,
            activityLevel = param.activityLevel,
            goal = param.goal
        )
    }
}