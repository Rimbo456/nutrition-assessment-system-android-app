package com.example.nutrition_assessment_system_android_app.data.nutrition.mapper

import com.example.nutrition_assessment_system_android_app.data.nutrition.datasource.remote.response.AnalyzeResponse
import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.model.NutritionItem
import com.example.nutrition_assessment_system_android_app.domain.model.TotalNutrition

fun AnalyzeResponse.toDomain(): Dish {
    val d = this.data
    return Dish(
        name = d.dish,
        totalWeightG = d.totalWeightG,
        ingredients = d.ingredients,
        nutrition = d.nutrition.mapValues { (_, n) ->
            NutritionItem(
                weightG = n.weightG,
                calories = n.calories,
                proteinG = n.proteinG,
                fatG = n.fatG,
                carbsG = n.carbsG,
            )
        },
        totalNutrition = TotalNutrition(
            calories = d.totalNutrition.calories,
            proteinG = d.totalNutrition.proteinG,
            fatG = d.totalNutrition.fatG,
            carbsG = d.totalNutrition.carbsG,
        ),
    )
}