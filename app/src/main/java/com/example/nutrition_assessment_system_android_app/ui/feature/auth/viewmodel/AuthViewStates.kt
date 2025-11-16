package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import android.content.Intent
import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.ui.interfaces.OneTimeEvent
import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewState

class AuthViewStates {
    data class AuthViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isAuthenticated: Boolean = false,
        val navigateToSignUpScreen: OneTimeEvent<Boolean>? = null,
        val registrationSuccess: OneTimeEvent<User>? = null,
        val loginSuccess: OneTimeEvent<User>? = null,
        val googleSignInIntent: OneTimeEvent<Intent>? = null
    ) : ViewState()

    data class AuthViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isAuthenticated: Boolean = false,
        val navigateToSignUpScreen: OneTimeEvent<Boolean>? = null,
        val registrationSuccess: OneTimeEvent<User>? = null,
        val loginSuccess: OneTimeEvent<User>? = null,
        val googleSignInIntent: OneTimeEvent<Intent>? = null
    ) : ViewModelState() {
        override fun toUiState(): ViewState {
            return AuthViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                isAuthenticated = isAuthenticated,
                navigateToSignUpScreen = navigateToSignUpScreen,
                registrationSuccess = registrationSuccess,
                loginSuccess = loginSuccess,
                googleSignInIntent = googleSignInIntent
            )
        }
    }
}