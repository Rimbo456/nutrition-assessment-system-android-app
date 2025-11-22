package com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote

import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.request.SendMessageRequest
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.response.CreateSessionResponse
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.response.GetMessagesResponse
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.response.GetSessionsResponse
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.response.SendMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApiService {

    @POST("api/chat/sendMessage")
    suspend fun sendMessage(@Body sendMessageRequest: SendMessageRequest): Response<SendMessageResponse>

    @GET("api/chat/messages")
    suspend fun getMessages(@Query("sessionId") sessionId: String): Response<GetMessagesResponse>

    @POST("api/chat/session")
    suspend fun createSession(): Response<CreateSessionResponse>

    @GET("api/chat/sessions")
    suspend fun getSessions(): Response<GetSessionsResponse>

}