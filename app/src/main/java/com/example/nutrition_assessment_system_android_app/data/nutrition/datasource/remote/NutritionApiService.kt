package com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.dto.MealDto
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.request.GetMealsByDateRequest
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.request.SaveMealRequest
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.AnalyzeResponse
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.GetMealsByDateResponse
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.SaveMealResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NutritionApiService {
    @Multipart
    @POST("/api/nutrition/analyze")
    suspend fun analyzePhoto(@Part image: MultipartBody.Part): Response<AnalyzeResponse>

    @POST("/api/nutrition/saveMeal")
    suspend fun saveMeal(@Body saveMealRequest: SaveMealRequest): Response<SaveMealResponse>

    @POST("/api/nutrition/meals")
    suspend fun getMealsByDate(@Body getMealsByDateRequest: GetMealsByDateRequest): Response<GetMealsByDateResponse>
}