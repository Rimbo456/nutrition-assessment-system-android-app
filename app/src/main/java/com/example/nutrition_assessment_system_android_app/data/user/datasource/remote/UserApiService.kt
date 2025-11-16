package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote

import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.UserDto
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request.LoginRequest
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request.RegisterRequest
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.response.LoginResponse
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}