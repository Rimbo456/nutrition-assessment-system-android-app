package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.NumberPicker
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun TargetWeightScreen(
    viewModel: OnBoardingViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = { navController.navigate("onboarding/goal") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Button(
                        onClick = { navController.navigate("onboarding/weekly-rate") },
                        enabled = uiState.isWeightValid,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tiếp tục")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnboardingProgressIndicator(
                currentStep = 7,
                totalSteps = 8
            )

            OnboardingHeader(
                title = "Cân nặng mục tiêu",
                subtitle = "Đặt mục tiêu cân nặng của bạn để tính toán kế hoạch ăn uống phù hợp"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Weight suggestion based on height and goal
            val height = (uiState.height ?: 170.0).toDouble()
            val currentWeight = (uiState.weight ?: 70.0).toDouble()
            val minBmi = 18.5
            val maxBmi = 24.9
            val heightInMeters = height / 100.0
            val minSuggestedWeight = (minBmi * heightInMeters * heightInMeters).toInt()
            val maxSuggestedWeight = (maxBmi * heightInMeters * heightInMeters).toInt()

            val suggestionText = when (uiState.goal) {
                "lose_weight" -> "Gợi ý: ${minSuggestedWeight} - ${maxSuggestedWeight} kg cho chỉ số BMI khỏe mạnh"
                "gain_weight" -> "Gợi ý: ${minSuggestedWeight} - ${maxSuggestedWeight} kg cho chỉ số BMI khỏe mạnh"
                "build_muscle" -> "Gợi ý: ${maxSuggestedWeight + 3} kg để xây dựng cơ bắp"
                "maintain_weight" -> "Gợi ý: Giữ quanh ${currentWeight.toInt()} kg"
                "improve_health" -> "Gợi ý: ${minSuggestedWeight} - ${maxSuggestedWeight} kg để cải thiện sức khỏe"
                else -> "Gợi ý: ${minSuggestedWeight} - ${maxSuggestedWeight} kg"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = suggestionText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            NumberPicker(
                value = uiState.weight?.toInt() ?: 65,
                onValueChange = { TODO() },
                minValue = 30,
                maxValue = 300,
                unit = "kg",
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

