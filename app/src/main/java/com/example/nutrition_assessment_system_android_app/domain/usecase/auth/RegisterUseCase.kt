package com.example.nutrition_assessment_system_android_app.domain.usecase.auth

import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.params.RegisterParams
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<RegisterParams, Resource<User>>() {

    override suspend fun execute(param: RegisterParams): Resource<User> {

        if (!param.email.contains("@")) {
            return Resource.Error("Invalid email")
        }

        if (param.name.isBlank()) {
            return Resource.Error("Name cannot be blank")
        }

        if (param.password.length < 6) {
            return Resource.Error("Password must be at least 6 characters long")
        }

        return userRepository.registerUser(
            name = param.name,
            email = param.email,
            password = param.password
        )
    }
}