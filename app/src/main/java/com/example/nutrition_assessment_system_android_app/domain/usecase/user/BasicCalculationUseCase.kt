package com.example.nutrition_assessment_system_android_app.domain.usecase.user

import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.domain.util.UseCase
import javax.inject.Inject
import kotlin.math.round

class BasicCalculationUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Resource<NutritionResult>>() {
    override suspend fun execute(param: Unit): Resource<NutritionResult> {
        when (val user = userRepository.getCurrentUser()) {
            is Resource.Loading -> {
                return Resource.Loading()
            }

            is Resource.Error -> {
                return Resource.Error(user.message)
            }

            is Resource.Success -> {
                val userData = user.data

                // 1. CHECK AN TOÀN: Đảm bảo dữ liệu không null
                if (userData == null || userData.weight == null || userData.height == null || userData.age == null) {
                    return Resource.Error("Thiếu thông tin người dùng để tính toán")
                }

                val weight = userData.weight
                val height = userData.height
                val age = userData.age
                // Weekly Rate mặc định là 0 nếu null (Maintain weight)
                val weeklyRate = userData.weeklyRate ?: 0.0

                // 2. TÍNH BMR (Mifflin-St Jeor)
                val bmr = if (userData.gender == "male") {
                    10 * weight + 6.25 * height - 5 * age + 5
                } else {
                    10 * weight + 6.25 * height - 5 * age - 161
                }

                // 3. TÍNH TDEE
                val tdee = when (userData.activityLevel) {
                    "sedentary" -> bmr * 1.2
                    "light" -> bmr * 1.375
                    "moderate" -> bmr * 1.55
                    "active" -> bmr * 1.725
                    "very_active" -> bmr * 1.9
                    else -> bmr * 1.2
                }

                // 4. TÍNH CALORIES TARGET (QUAN TRỌNG: Dựa trên Weekly Rate)
                // 1kg mỡ ~ 7700kcal. 1 ngày cần thâm hụt: (Rate * 7700) / 7 = Rate * 1100
                // weeklyRate dương -> Tăng cân (+), Âm -> Giảm cân (-)
                val dailyCalorieAdjustment = weeklyRate * 1100.0

                // Đảm bảo không xuống dưới mức BMR (ngưỡng an toàn sức khỏe tối thiểu)
                // Trừ khi có chỉ định bác sĩ, không nên ăn dưới BMR quá sâu.
                var caloriesTarget = tdee + dailyCalorieAdjustment

                // Safety Check: Không để Calo âm hoặc quá thấp (ví dụ < 1000)
                if (caloriesTarget < 1000) caloriesTarget = 1000.0

                // 5. TÍNH MACRO
                val proteinTarget = when (userData.goal) {
                    "build_muscle", "gain_weight", "active" -> weight * 1.8
                    "lose_weight" -> weight * 1.6
                    "maintain_weight", "improve_health" -> weight * 1.3
                    else -> weight * 1.5
                }

                val fatTarget = (caloriesTarget * 0.25) / 9.0 // 25% Calo từ Fat

                // Tính Carb còn lại. Max(0.0) để đảm bảo không bị âm
                val carbTarget = maxOf(0.0, (caloriesTarget - (proteinTarget * 4.0 + fatTarget * 9.0)) / 4.0)

                // 6. TÍNH BMI
                val heightInMeter = height / 100.0
                val bmi = weight / (heightInMeter * heightInMeter)

                // 7. TÍNH NƯỚC (Hydration)
                // Quy tắc: 1ml cho mỗi 1kcal TDEE + Bù nước vận động
                val waterBase = tdee // 1 kcal = 1 ml
                val waterExtra = when (userData.activityLevel) {
                    "moderate" -> 300.0
                    "active" -> 500.0
                    "very_active" -> 700.0
                    else -> 0.0
                }
                val waterTarget = waterBase + waterExtra

                return Resource.Success(
                    data = NutritionResult(
                        bmr = round(bmr),
                        tdee = round(tdee),
                        bmi = round(bmi * 10) / 10.0, // Làm tròn 1 chữ số thập phân
                        caloriesTarget = round(caloriesTarget),
                        proteinTarget = round(proteinTarget),
                        fatTarget = round(fatTarget),
                        carbTarget = round(carbTarget),
                        waterTarget = round(waterTarget)
                    )
                )
            }
        }
    }
}

data class NutritionResult(
    val bmr: Double,
    val tdee: Double,
    val bmi: Double,
    val caloriesTarget: Double,
    val proteinTarget: Double,
    val fatTarget: Double,
    val carbTarget: Double,
    val waterTarget: Double
)