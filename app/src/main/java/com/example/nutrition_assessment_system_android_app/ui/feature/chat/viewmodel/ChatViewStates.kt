package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class ChatViewStates {
    data class ChatViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val messages: List<String> = emptyList()
    ): ViewState()

    data class ChatViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val messages: List<String> = emptyList()
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return ChatViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                messages = messages
            )
        }
    }
}