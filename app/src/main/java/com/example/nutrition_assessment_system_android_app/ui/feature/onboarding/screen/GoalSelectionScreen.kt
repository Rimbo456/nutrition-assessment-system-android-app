package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.SelectionCard
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun GoalSelectionScreen(
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
                        onClick = {
                            //TODO
                        },
                        enabled = uiState.isGoalValid,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Hoàn thành")
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
                currentStep = OnBoardingStep.GOAL.ordinal,
                totalSteps = OnBoardingStep.entries.size
            )

            OnboardingHeader(
                title = "Mục tiêu của bạn",
                subtitle = "Chọn mục tiêu sức khỏe để nhận được kế hoạch dinh dưỡng phù hợp"
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectionCard(
                title = "Giảm cân",
                description = "Giảm mỡ thừa và đạt cân nặng mục tiêu",
                icon = Lucide.TrendingDown,
                isSelected = uiState.goal == "lose_weight",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGoal("lose_weight")) }
            )

            SelectionCard(
                title = "Duy trì cân nặng",
                description = "Giữ vững cân nặng hiện tại và cải thiện sức khỏe",
                icon = Lucide.Equal,
                isSelected = uiState.goal == "maintain_weight",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGoal("maintain_weight")) }
            )

            SelectionCard(
                title = "Tăng cân",
                description = "Tăng khối lượng cơ bắp và cân nặng lành mạnh",
                icon = Lucide.TrendingUp,
                isSelected = uiState.goal == "gain_weight",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGoal("gain_weight")) }
            )

            SelectionCard(
                title = "Xây dựng cơ bắp",
                description = "Tập trung vào tăng cơ và giảm mỡ",
                icon = Lucide.Dumbbell,
                isSelected = uiState.goal == "build_muscle",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGoal("build_muscle")) }
            )

            SelectionCard(
                title = "Cải thiện sức khỏe",
                description = "Tăng cường sức khỏe tổng thể và năng lượng",
                icon = Lucide.Heart,
                isSelected = uiState.goal == "improve_health",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGoal("improve_health")) }
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

