package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class PreferencesDto(
    @SerializedName("dietType")
    val dietType: String? = null,

    @SerializedName("unit")
    val unit: String? = null,

    @SerializedName("language")
    val language: String? = null
)