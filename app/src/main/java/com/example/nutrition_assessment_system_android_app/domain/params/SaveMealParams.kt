package com.example.nutrition_assessment_system_android_app.domain.params

import com.example.nutrition_assessment_system_android_app.domain.model.Dish

data class SaveMealParams(
    val dish: Dish,
    val type: Int?
)