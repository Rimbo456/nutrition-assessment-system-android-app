package com.example.nutrition_assessment_system_android_app.domain.params

data class GetMessagesParams(
    val sessionId: String,
    val forceRefresh: Boolean
)