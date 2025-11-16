package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import android.content.Intent
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface AuthIntent : ViewIntent {
    data class LoginWithEmailAndPassword(val email: String, val password: String) : AuthIntent
    data class LoginWithGoogle(val data: Intent?) : AuthIntent
    data class Register(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : AuthIntent
    object GetSignInIntent: AuthIntent
    object GetAuthToken : AuthIntent
    data class SetAuthToken(val token: String) : AuthIntent
    object Logout : AuthIntent
    object NavigateToSignUpScreen : AuthIntent
    object CheckUserSession : AuthIntent
}