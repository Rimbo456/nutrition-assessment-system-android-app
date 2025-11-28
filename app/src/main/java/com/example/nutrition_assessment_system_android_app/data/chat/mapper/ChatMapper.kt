package com.example.nutrition_assessment_system_android_app.data.chat.mapper

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.MessageEntity
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.MessageDto
import com.example.nutrition_assessment_system_android_app.domain.model.Message

fun MessageDto.toDomain(): Message {
    return Message(
        id = this.id,
        sessionId = this.sessionId,
        role = this.role,
        text = this.text,
        createdAt = this.createdAt,
    )
}

fun MessageDto.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        sessionId = this.sessionId,
        role = this.role,
        text = this.text,
        createdAt = this.createdAt,
    )
}

fun MessageEntity.toDomain(): Message {
    return Message(
        id = this.id,
        sessionId = this.sessionId,
        role = this.role,
        text = this.text,
        createdAt = this.createdAt,
    )
}

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        sessionId = this.sessionId,
        role = this.role,
        text = this.text,
        createdAt = this.createdAt,
    )
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        id = this.id,
        sessionId = this.sessionId,
        role = this.role,
        text = this.text,
        createdAt = this.createdAt,
    )
}