package com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.response

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.ChatSessionDto

data class GetSessionsResponse(
    val sessions: List<ChatSessionDto>
)