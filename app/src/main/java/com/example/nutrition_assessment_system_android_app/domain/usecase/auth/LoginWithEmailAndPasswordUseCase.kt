package com.example.nutrition_assessment_system_android_app.domain.usecase.auth

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.params.LoginWithEmailAndPasswordParams
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class LoginWithEmailAndPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
): UseCase<LoginWithEmailAndPasswordParams, Resource<User>>() {
    override suspend fun execute(param: LoginWithEmailAndPasswordParams): Resource<User> {
        if (!param.email.contains("@")) {
            return Resource.Error("Invalid email")
        }

        if (param.password.length < 6) {
            return Resource.Error("Password must be at least 6 characters long")
        }

        return userRepository.loginWithEmailAndPassword(
            email = param.email,
            password = param.password
        )
    }
}