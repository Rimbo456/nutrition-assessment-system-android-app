package com.example.nutrition_assessment_system_android_app.data.nutrition.repository

import com.example.nutrition_assessment_system_android_app.data.common.util.ApiHelper
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.NutritionApiService
import com.example.nutrition_assessment_system_android_app.data.nutrition.mapper.toDomain
import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.repository.NutritionRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class NutritionRepositoryImpl @Inject constructor(
    private val nutritionApiService: NutritionApiService
): NutritionRepository {
    override suspend fun analyzePhoto(image: File): Resource<Dish> {
        return ApiHelper.safeApiCall(
            apiCall = {
                val requestBody = image.asRequestBody("image/jpg".toMediaType())
                val multipart = MultipartBody.Part.createFormData("file", image.name, requestBody)
                nutritionApiService.analyzePhoto(multipart)
            },
            transform = { response ->
                response.toDomain()
            }
        )
    }
}