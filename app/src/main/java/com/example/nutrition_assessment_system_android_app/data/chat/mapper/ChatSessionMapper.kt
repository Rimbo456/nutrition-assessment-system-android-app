package com.example.nutrition_assessment_system_android_app.data.chat.mapper

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.ChatSessionEntity
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.ChatSessionDto
import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession

fun ChatSessionEntity.toDomain(): ChatSession {
    return ChatSession(
        id = this.id,
        userId = this.userId,
        title = this.title,
        lastMessage = this.lastMessage,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun ChatSessionDto.toEntity(): ChatSessionEntity {
    return ChatSessionEntity(
        id = this.id,
        userId = this.userId,
        title = this.title,
        lastMessage = this.lastMessage,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun ChatSessionDto.toDomain(): ChatSession {
    return ChatSession(
        id = this.id,
        userId = this.userId,
        title = this.title,
        lastMessage = this.lastMessage,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
