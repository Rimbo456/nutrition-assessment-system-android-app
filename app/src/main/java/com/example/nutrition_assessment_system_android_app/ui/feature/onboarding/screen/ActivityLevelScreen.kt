package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Activity
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.SelectionCard
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun ActivityLevelScreen(
    viewModel: OnBoardingViewModel,
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
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.NavigateBack) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Button(
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.NavigateNext) },
                        enabled = uiState.isActivityLevelValid,
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OnboardingProgressIndicator(
                currentStep = OnBoardingStep.ACTIVITY.ordinal,
                totalSteps = OnBoardingStep.entries.size
            )

            OnboardingHeader(
                title = "Mức độ hoạt động",
                subtitle = "Chọn mức độ hoạt động thể chất phù hợp với sinh hoạt hàng ngày"
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectionCard(
                title = "Ít vận động",
                description = "Ít hoặc không tập thể dục, làm việc văn phòng",
                icon = Lucide.Activity,
                isSelected = uiState.activityLevel == "sedentary",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetActivityLevel("sedentary")) }
            )

            SelectionCard(
                title = "Vận động nhẹ",
                description = "Tập thể dục nhẹ 1-3 ngày/tuần",
                icon = Lucide.Activity,
                isSelected = uiState.activityLevel == "light",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetActivityLevel("light")) }
            )

            SelectionCard(
                title = "Vận động trung bình",
                description = "Tập thể dục vừa phải 3-5 ngày/tuần",
                icon = Lucide.Activity,
                isSelected = uiState.activityLevel == "moderate",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetActivityLevel("moderate")) }
            )

            SelectionCard(
                title = "Vận động nhiều",
                description = "Tập thể dục mạnh 6-7 ngày/tuần",
                icon = Lucide.Activity,
                isSelected = uiState.activityLevel == "active",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetActivityLevel("active")) }
            )

            SelectionCard(
                title = "Vận động rất nhiều",
                description = "Vận động viên hoặc công việc nặng nhọc",
                icon = Lucide.Activity,
                isSelected = uiState.activityLevel == "very_active",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetActivityLevel("very_active")) }
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

