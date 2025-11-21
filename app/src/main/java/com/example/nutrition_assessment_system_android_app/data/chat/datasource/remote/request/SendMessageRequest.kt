package com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.request

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.MessageDto

data class SendMessageRequest(
    val message: MessageDto
)