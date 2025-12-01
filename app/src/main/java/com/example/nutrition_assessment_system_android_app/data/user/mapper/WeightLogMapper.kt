package com.example.nutrition_assessment_system_android_app.data.user.mapper

import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.WeightLogEntity
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.WeightLogDto
import com.example.nutrition_assessment_system_android_app.domain.model.WeightLog

fun WeightLogDto.toEntity(): WeightLogEntity {
    return WeightLogEntity(
        id = this.id,
        weight = this.weight,
        timestamp = this.timestamp,
        dateString = this.dateString,
    )
}

fun WeightLogEntity.toDomain(): WeightLog {
    return WeightLog(
        id = this.id,
        weight = this.weight,
        timestamp = this.timestamp,
        dateString = this.dateString,
    )
}