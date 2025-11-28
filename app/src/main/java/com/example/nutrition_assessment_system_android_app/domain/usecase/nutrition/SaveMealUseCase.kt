package com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition

import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.model.Meal
import com.example.nutrition_assessment_system_android_app.domain.repository.NutritionRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class SaveMealUseCase @Inject constructor(
    private val nutritionRepository: NutritionRepository
): UseCase<Dish, Resource<Meal>>() {
    override suspend fun execute(param: Dish): Resource<Meal> {
        return nutritionRepository.saveMeal(param)
    }
}