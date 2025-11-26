package com.example.nutrition_assessment_system_android_app.domain.usecase.nutrition

import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.repository.NutritionRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import java.io.File
import javax.inject.Inject

class AnalyzeImageUseCase @Inject constructor(
    private val nutritionRepository: NutritionRepository
): UseCase<File, Resource<Dish>>() {
    override suspend fun execute(param: File): Resource<Dish> {
        return nutritionRepository.analyzePhoto(param)
    }
}