package com.example.nutrition_assessment_system_android_app.data.user.mapper

import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserEntity
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.UserDto

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        avatar = this.avatar,
        gender = this.gender,
        age = this.age,
        weight = this.weight,
        height = this.height,
        activityLevel = this.activityLevel,
        goal = this.goal,
        preferences = this.preferences,
        createAt = this.createAt,
    )
}

fun UserEntity.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        avatar = this.avatar,
        gender = this.gender,
        age = this.age,
        weight = this.weight,
        height = this.height,
        activityLevel = this.activityLevel,
        goal = this.goal,
        preferences = this.preferences,
        createAt = this.createAt,
    )
}