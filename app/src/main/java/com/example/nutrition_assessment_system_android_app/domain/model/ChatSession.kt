package com.example.nutrition_assessment_system_android_app.domain.model

data class ChatSession(
    val id: String,
    val userId: String,
    val title: String?,
    val lastMessage: String?,
    val createdAt: String,
    val updatedAt: String,
)
