package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.response

import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.WeightLogDto

data class GetWeightLogResponse(
    val success: Boolean,
    val data: List<WeightLogDto>
)