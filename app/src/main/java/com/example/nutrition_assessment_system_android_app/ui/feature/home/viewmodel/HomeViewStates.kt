package com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.OneTimeEvent
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class HomeViewStates {
    data class HomeViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val navigateToOnBoarding: OneTimeEvent<Boolean>? = null,
        val showLoginSuccessToast: OneTimeEvent<Boolean>? = null
    ): ViewState()

    data class HomeViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val navigateToOnBoarding: OneTimeEvent<Boolean>? = null,
        val showLoginSuccessToast: OneTimeEvent<Boolean>? = null
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return HomeViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                navigateToOnBoarding = navigateToOnBoarding,
                showLoginSuccessToast = showLoginSuccessToast
            )
        }
    }
}