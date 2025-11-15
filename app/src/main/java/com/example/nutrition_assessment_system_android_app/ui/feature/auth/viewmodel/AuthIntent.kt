package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewIntent

sealed interface AuthIntent : ViewIntent {
    data class Login(val email: String, val password: String) : AuthIntent
    data class Register(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : AuthIntent

    object Logout : AuthIntent
    object NavigateToSignUpScreen : AuthIntent
}