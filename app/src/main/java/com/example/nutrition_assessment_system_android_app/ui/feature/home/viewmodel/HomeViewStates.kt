package com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class HomeViewStates {
    data class HomeViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    ): ViewState()

    data class HomeViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return HomeViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
            )
        }
    }
}