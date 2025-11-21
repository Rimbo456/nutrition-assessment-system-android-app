package com.example.nutrition_assessment_system_android_app.data.chat.repository

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.ChatSessionDao
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.ChatApiService
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.MessageDao
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.ChatSessionDto
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.dto.MessageDto
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.request.GetMessagesRequest
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.request.SendMessageRequest
import com.example.nutrition_assessment_system_android_app.data.chat.mapper.toDomain
import com.example.nutrition_assessment_system_android_app.data.chat.mapper.toDto
import com.example.nutrition_assessment_system_android_app.data.chat.mapper.toEntity
import com.example.nutrition_assessment_system_android_app.data.common.util.ApiHelper
import com.example.nutrition_assessment_system_android_app.data.common.util.NetworkBoundResource
import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.domain.model.Message
import com.example.nutrition_assessment_system_android_app.domain.repository.ChatRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApiService: ChatApiService,
    private val messageDao: MessageDao,
    private val chatSessionDao: ChatSessionDao
): ChatRepository {

    override fun getMessages(
        sessionId: String,
        forceRefresh: Boolean
    ): Flow<Resource<List<Message>>> {
        return object : NetworkBoundResource<List<Message>, List<MessageDto>>() {
            override fun loadFromDb(): Flow<List<Message>> {
                return messageDao.getMessages(sessionId).map { list ->
                    list.map { it.toDomain() }
                }
            }

            override fun shouldFetch(data: List<Message>?): Boolean {
                return forceRefresh || data == null || data.isEmpty()
            }

            override suspend fun createCall(): List<MessageDto> {
                val res = chatApiService.getMessages(GetMessagesRequest(sessionId))
                if (res.isSuccessful) {
                    return res.body()?.messages ?: emptyList()
                } else throw Exception("Api error code: ${res.code()}")
            }

            override suspend fun saveCallResult(data: List<MessageDto>) {
                messageDao.insertMessages(data.map { it.toEntity() })
            }
        }.asFlow()
    }

    override suspend fun createSession(title: String?): Resource<ChatSession> {
        return ApiHelper.safeApiCall(
            apiCall = {
                chatApiService.createSession()
            },
            transform = { response ->
                response.session.toDomain()
            }
        )
    }

    override suspend fun sendMessage(message: Message): Resource<Message> {
        return ApiHelper.safeApiCall(
            apiCall = {
                chatApiService.sendMessage(
                    SendMessageRequest(
                        message = message.toDto()
                    )
                )
            },
            transform = { response ->
                response.message.toDomain()
            }
        )
    }

    override fun getSessions(forceRefresh: Boolean): Flow<Resource<List<ChatSession>>> {
        return object : NetworkBoundResource<List<ChatSession>, List<ChatSessionDto>>() {
            override fun loadFromDb(): Flow<List<ChatSession>> {
                return chatSessionDao.getAllSessions().map { list ->
                    list.map { it.toDomain() }
                }
            }

            override fun shouldFetch(data: List<ChatSession>?): Boolean {
                return forceRefresh || data == null || data.isEmpty()
            }

            override suspend fun createCall(): List<ChatSessionDto> {
                val res = chatApiService.getSessions()
                if (res.isSuccessful) {
                    return res.body()?.sessions ?: emptyList()
                } else throw Exception("Api error code: ${res.code()}")
            }

            override suspend fun saveCallResult(data: List<ChatSessionDto>) {
                chatSessionDao.insertSession(data.map { it.toEntity() })
            }

        }.asFlow()
    }
}