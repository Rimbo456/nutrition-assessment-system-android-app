package com.example.nutrition_assessment_system_android_app.domain.usecase.user

import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject

class BasicCalculationUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Resource<Map<String, Double>>>() {
    override suspend fun execute(param: Unit): Resource<Map<String, Double>> {
        var bmr = 0.0
        var tdee = 0.0
        var caloriesTarget = 0.0
        var bmi = 0.0
        var proteinTarget = 0.0
        var fatTarget = 0.0
        var carbTarget = 0.0
        var waterTarget = 0.0
        when (val user = userRepository.getCurrentUser()) {
            is Resource.Loading -> {
                return Resource.Loading()
            }

            is Resource.Error -> {
                return Resource.Error(user.message)
            }

            is Resource.Success -> {
                val userData = user.data
                if (userData.gender == "male") {
                    bmr = 10 * userData.weight!! + 6.25 * userData.height!! - 5 * userData.age!! + 5
                } else {
                    bmr =
                        10 * userData.weight!! + 6.25 * userData.height!! - 5 * userData.age!! - 161
                }
                tdee = when (userData.activityLevel) {
                    "sedentary" -> bmr * 1.2
                    "light" -> bmr * 1.375
                    "moderate" -> bmr * 1.55
                    "active" -> bmr * 1.725
                    "very_active" -> bmr * 1.9
                    else -> bmr * 1.2
                }
                caloriesTarget = when (userData.goal) {
                    "lose_weight" -> tdee - 500
                    "maintain_weight" -> tdee
                    "gain_weight" -> tdee + 500
                    "build_muscle" -> tdee + 300
                    "improve_health" -> tdee
                    else -> tdee
                }
                proteinTarget = when (userData.goal) {
                    "build_muscle", "gain_weight", "active" -> userData.weight * 1.8 // Tăng cơ, Tăng cân, Vận động viên
                    "lose_weight" -> userData.weight * 1.6 // Giảm cân (cần protein cao hơn để giữ cơ)
                    "maintain_weight", "improve_health" -> userData.weight * 1.3 // Mức duy trì cơ bản
                    else -> userData.weight * 1.5
                }
                fatTarget = (caloriesTarget * 0.25) / 9
                carbTarget = (caloriesTarget - (proteinTarget * 4 + fatTarget * 9)) / 4
                bmi = userData.weight / ((userData.height / 100.0) * (userData.height / 100.0))
                waterTarget = when (userData.activityLevel) {
                    "active" -> tdee + 300 // ml
                    "very_active" -> tdee + 500 // ml
                    else -> tdee // ml
                }
                return Resource.Success(
                    data = mapOf(
                        "BMR" to bmr,
                        "TDEE" to tdee,
                        "BMI" to bmi,
                        "CaloriesTarget" to caloriesTarget,
                        "ProteinTarget" to proteinTarget,
                        "FatTarget" to fatTarget,
                        "CarbTarget" to carbTarget,
                        "WaterTarget" to waterTarget
                    )
                )
            }
        }
    }
}