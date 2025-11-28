package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface ChatIntent : ViewIntent {
    data class SendMessage(val message: String, val sessionId: String) : ChatIntent
    data class CreateNewChatSession(val sessionName: String) : ChatIntent
    data class LoadChatSessionHistory(val forceRefresh: Boolean) : ChatIntent
    data class SelectChatSession(val session: ChatSession) : ChatIntent
    object BackToChatSessionsList : ChatIntent
    data class LoadChatMessagesHistory(val sessionId: String, val forceRefresh: Boolean) : ChatIntent
}