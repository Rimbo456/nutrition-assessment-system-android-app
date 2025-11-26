package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.AnalyzeResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part

interface NutritionApiService {
    @POST("/api/camera/analyze")
    suspend fun analyzePhoto(@Part image: MultipartBody.Part): Response<AnalyzeResponse>
}