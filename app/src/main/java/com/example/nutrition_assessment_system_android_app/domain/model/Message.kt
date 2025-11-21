package com.example.nutrition_assessment_system_android_app.domain.model

data class Message(
    val id: String,
    val sessionId: String,
    val role: String,
    val text: String,
    val createdAt: Long,
)
