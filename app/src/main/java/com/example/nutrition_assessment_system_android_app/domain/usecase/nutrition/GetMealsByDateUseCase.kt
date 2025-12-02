package com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition

import com.example.nutrition_assessment_system_android_app.domain.model.Meal
import com.example.nutrition_assessment_system_android_app.domain.repository.NutritionRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class GetMealsByDateUseCase @Inject constructor(
    private val nutritionRepository: NutritionRepository
): UseCase<String, Resource<List<Meal>>>() {
    override suspend fun execute(param: String): Resource<List<Meal>> {
        return nutritionRepository.getMealsByDate(
            date = param
        )
    }
}