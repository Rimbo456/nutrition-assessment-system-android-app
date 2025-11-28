package com.example.nutrition_assessment_system_android_app.data.nutrition.mapper

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.local.MealEntity
import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.dto.MealDto
import com.example.nutrition_assessment_system_android_app.domain.model.Meal

fun MealDto.toDomain(): Meal {
    return Meal(
        id = this.id,
        userId = this.userId,
        dishLabel = this.dishLabel,
        servingSize = this.servingSize,
        components = this.components.mapValues { (_, n) ->
            com.example.nutrition_assessment_system_android_app.domain.model.NutritionItem(
                weightG = n.weightG,
                calories = n.calories,
                proteinG = n.proteinG,
                fatG = n.fatG,
                carbsG = n.carbsG,
            )
        },
        totalNutrition = com.example.nutrition_assessment_system_android_app.domain.model.TotalNutrition(
            calories = this.totalNutrition.calories,
            proteinG = this.totalNutrition.proteinG,
            fatG = this.totalNutrition.fatG,
            carbsG = this.totalNutrition.carbsG,
        ),
        date = this.date,
        type = this.type,
        createAt = this.createAt,
    )
}

fun MealDto.toEntity(): MealEntity {
    return MealEntity(
        id = this.id,
        userId = this.userId,
        dishLabel = this.dishLabel,
        servingSize = this.servingSize,
        components = this.components.mapValues { (_, n) ->
            com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.NutritionItem(
                weightG = n.weightG,
                calories = n.calories,
                proteinG = n.proteinG,
                fatG = n.fatG,
                carbsG = n.carbsG,
            )
        },
        totalNutrition = com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.common.TotalNutrition(
            calories = this.totalNutrition.calories,
            proteinG = this.totalNutrition.proteinG,
            fatG = this.totalNutrition.fatG,
            carbsG = this.totalNutrition.carbsG,
        ),
        date = this.date,
        type = this.type,
        createAt = this.createAt,
    )
}