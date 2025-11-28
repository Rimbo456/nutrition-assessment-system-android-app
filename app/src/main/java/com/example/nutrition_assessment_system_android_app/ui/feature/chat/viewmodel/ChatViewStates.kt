package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.domain.model.Message
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class ChatViewStates {
    data class ChatViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val messages: List<Message> = emptyList(),
        val currentSession: ChatSession? = null,
        val allSessions : List<ChatSession> = emptyList(),
        val isBotResponse: Boolean = false
    ): ViewState()

    data class ChatViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val messages: List<Message> = emptyList(),
        val currentSession: ChatSession? = null,
        val allSessions : List<ChatSession> = emptyList(),
        val isBotResponse: Boolean = false
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return ChatViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                messages = messages,
                currentSession = currentSession,
                allSessions = allSessions,
                isBotResponse = isBotResponse
            )
        }
    }
}