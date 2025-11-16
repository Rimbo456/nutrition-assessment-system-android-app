package com.example.nutrition_assessment_system_android_app.data.common.util

import androidx.room.TypeConverter
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.PreferencesDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPreferences(preferencesDto: PreferencesDto?): String? {
        return Gson().toJson(preferencesDto)
    }

    @TypeConverter
    fun toPreferences(value: String?): PreferencesDto? {
        if (value == null) return null
        return Gson().fromJson(value, PreferencesDto::class.java)
    }
}