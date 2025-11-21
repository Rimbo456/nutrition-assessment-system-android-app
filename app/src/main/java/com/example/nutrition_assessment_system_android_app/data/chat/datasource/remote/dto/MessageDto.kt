package com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto

data class MessageDto(
    val id: String,
    val sessionId: String,
    val role: String,
    val text: String,
    val createdAt: Long,
)