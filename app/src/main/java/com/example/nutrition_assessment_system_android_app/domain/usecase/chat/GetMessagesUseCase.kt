package com.example.nutrition_assessment_system_android_app.domain.usecase.chat

import com.example.nutrition_assessment_system_android_app.domain.model.Message
import com.example.nutrition_assessment_system_android_app.domain.params.GetMessagesParams
import com.example.nutrition_assessment_system_android_app.domain.repository.ChatRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
): UseCase<GetMessagesParams, Flow<Resource<List<Message>>>>() {
    override suspend fun execute(param: GetMessagesParams): Flow<Resource<List<Message>>> {
        return chatRepository.getMessages(
            sessionId = param.sessionId,
            forceRefresh = param.forceRefresh
        )
    }
}