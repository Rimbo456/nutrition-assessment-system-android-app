package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.composables.icons.lucide.Dumbbell
import com.composables.icons.lucide.Equal
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.TrendingDown
import com.composables.icons.lucide.TrendingUp
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.SelectionCard
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun GoalSelectionScreen(
    viewModel: OnBoardingViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        Log.d("GoalSelectionScreen", "UI State changed: $uiState")

        uiState.navigateToHome?.let { event ->
            navController.navigate("home") {
                popUpTo("onboarding") { inclusive = true }
            }
            event.onConsumed()
        }
    }

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
                        onClick = { navController.navigate("onboarding/activity") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Button(
                        onClick = {
                            viewModel.onTriggerIntent(OnBoardingIntent.SubmitOnboardingData)
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
                currentStep = 6,
                totalSteps = 6
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

