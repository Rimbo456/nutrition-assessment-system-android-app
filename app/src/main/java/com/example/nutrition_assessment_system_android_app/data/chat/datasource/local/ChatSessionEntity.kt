package com.example.nutrition_assessment_system_android_app.data.chat.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_sessions")
data class ChatSessionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String?,
    val lastMessage: String?,
    val createdAt: String,
    val updatedAt: String,
)
