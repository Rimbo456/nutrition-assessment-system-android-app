package com.example.nutrition_assessment_system_android_app.domain.usecase.auth

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.params.LoginWithGoogleParams
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<LoginWithGoogleParams, Resource<User>>() {
    override suspend fun execute(param: LoginWithGoogleParams): Resource<User> {
        if (param.googleToken.isBlank()) {
            return Resource.Error("Google token cannot be blank")
        }

        return userRepository.loginWithGoogle(
            googleToken = param.googleToken
        )
    }
}