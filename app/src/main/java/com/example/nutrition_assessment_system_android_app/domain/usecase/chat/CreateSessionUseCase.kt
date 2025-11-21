package com.example.nutrition_assessment_system_android_app.domain.usecase.chat

import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.domain.params.SendMessageParams
import com.example.nutrition_assessment_system_android_app.domain.repository.ChatRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val chatRepository: ChatRepository
): UseCase<SendMessageParams, Resource<ChatSession>>() {
    override suspend fun execute(param: SendMessageParams): Resource<ChatSession> {
        return chatRepository.createSession()
    }
}