package com.example.nutrition_assessment_system_android_app.data.user.mapper

import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.PreferencesDto
import com.example.nutrition_assessment_system_android_app.domain.model.Preferences

fun PreferencesDto.toPreferences(): Preferences {
    return Preferences(
        dietType = this.dietType,
        unit = this.unit,
        language = this.language
    )
}