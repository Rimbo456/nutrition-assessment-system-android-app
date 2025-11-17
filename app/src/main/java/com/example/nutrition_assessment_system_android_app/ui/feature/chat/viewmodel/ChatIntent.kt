package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface ChatIntent : ViewIntent {
    data class SendMessage(val message: String) : ChatIntent
}