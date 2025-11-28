package com.example.nutrition_assessment_system_android_app.domain.usecase.chat

import com.example.nutrition_assessment_system_android_app.domain.model.ChatSession
import com.example.nutrition_assessment_system_android_app.domain.params.GetSessionsParams
import com.example.nutrition_assessment_system_android_app.domain.repository.ChatRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
): UseCase<GetSessionsParams, Flow<Resource<List<ChatSession>>>>() {
    override suspend fun execute(param: GetSessionsParams): Flow<Resource<List<ChatSession>>> {
        return chatRepository.getSessions(
            forceRefresh = param.forceRefresh
        )
    }
}