package com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto

data class ChatSessionDto(
    val id: String,
    val userId: String,
    val title: String?,
    val lastMessage: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
