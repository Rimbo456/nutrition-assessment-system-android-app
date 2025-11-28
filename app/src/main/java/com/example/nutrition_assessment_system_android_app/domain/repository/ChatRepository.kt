package com.example.nutrition_assessment_system_android_app.domain.repository

import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.domain.model.Message
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getSessions(forceRefresh: Boolean = false): Flow<Resource<List<ChatSession>>>
    fun getMessages(sessionId: String, forceRefresh: Boolean = false): Flow<Resource<List<Message>>>

    suspend fun createSession(title: String? = null): Resource<ChatSession>

    suspend fun sendMessage(message: String, sessionId: String): Resource<Message>
}