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
import com.composables.icons.lucide.Activity
import com.composables.icons.lucide.Dumbbell
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.TrendingDown
import com.composables.icons.lucide.TrendingUp
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.SelectionCard
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel


@Composable
fun WeeklyRateScreen(
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
                        onClick = { navController.navigate("onboarding/target-weight") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Button(
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SubmitOnboardingData) },
                        enabled = uiState.isWeightValid,
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
                currentStep = 8,
                totalSteps = 8
            )

            OnboardingHeader(
                title = "Mức độ thay đổi cân nặng",
                subtitle = when (uiState.goal) {
                    "lose_weight" -> "Chọn mức độ giảm cân hàng tuần phù hợp với bạn"
                    "maintain_weight" -> "Duy trì cân nặng hiện tại một cách ổn định"
                    "gain_weight" -> "Chọn mức độ tăng cân hàng tuần phù hợp với bạn"
                    "build_muscle" -> "Chọn mức độ tăng cân để xây dựng cơ bắp"
                    "improve_health" -> "Chọn mức độ thay đổi cân nặng để cải thiện sức khỏe"
                    else -> "Chọn mức độ tăng hoặc giảm cân hàng tuần phù hợp với mục tiêu"
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Show options based on selected goal
            when (uiState.goal) {
                "lose_weight" -> {
                    SelectionCard(
                        title = "Giảm 0.25 kg/tuần",
                        description = "Giảm cân chậm, an toàn và bền vững (500 kcal/ngày)",
                        icon = Lucide.TrendingDown,
                        isSelected = uiState.weeklyRate == -0.25,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(-0.25)) }
                    )

                    SelectionCard(
                        title = "Giảm 0.5 kg/tuần",
                        description = "Giảm cân vừa phải, lành mạnh (1000 kcal/ngày)",
                        icon = Lucide.TrendingDown,
                        isSelected = uiState.weeklyRate == -0.5,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(-0.5)) }
                    )

                    SelectionCard(
                        title = "Giảm 0.75 kg/tuần",
                        description = "Giảm cân nhanh, cần theo dõi sức khỏe (1500 kcal/ngày)",
                        icon = Lucide.TrendingDown,
                        isSelected = uiState.weeklyRate == -0.75,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(-0.75)) }
                    )

                    SelectionCard(
                        title = "Giảm 1.0 kg/tuần",
                        description = "Giảm cân rất nhanh, nên có hướng dẫn chuyên gia (2000 kcal/ngày)",
                        icon = Lucide.TrendingDown,
                        isSelected = uiState.weeklyRate == -1.0,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(-1.0)) }
                    )
                }

                "maintain_weight" -> {
                    SelectionCard(
                        title = "Giữ nguyên cân nặng",
                        description = "Duy trì cân nặng hiện tại (0 kcal/ngày)",
                        icon = Lucide.Activity,
                        isSelected = uiState.weeklyRate == 0.0,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.0)) }
                    )
                }

                "gain_weight" -> {
                    SelectionCard(
                        title = "Tăng 0.25 kg/tuần",
                        description = "Tăng cân chậm, xây dựng cơ bắp (500 kcal/ngày)",
                        icon = Lucide.TrendingUp,
                        isSelected = uiState.weeklyRate == 0.25,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.25)) }
                    )

                    SelectionCard(
                        title = "Tăng 0.5 kg/tuần",
                        description = "Tăng cân vừa phải, xây dựng cơ bắp (1000 kcal/ngày)",
                        icon = Lucide.TrendingUp,
                        isSelected = uiState.weeklyRate == 0.5,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.5)) }
                    )

                    SelectionCard(
                        title = "Tăng 0.75 kg/tuần",
                        description = "Tăng cân nhanh, cần theo dõi sức khỏe (1500 kcal/ngày)",
                        icon = Lucide.TrendingUp,
                        isSelected = uiState.weeklyRate == 0.75,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.75)) }
                    )
                }

                "build_muscle" -> {
                    SelectionCard(
                        title = "Tăng 0.25 kg/tuần",
                        description = "Xây dựng cơ bắp chậm (500 kcal/ngày)",
                        icon = Lucide.Dumbbell,
                        isSelected = uiState.weeklyRate == 0.25,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.25)) }
                    )

                    SelectionCard(
                        title = "Tăng 0.5 kg/tuần",
                        description = "Xây dựng cơ bắp vừa phải (1000 kcal/ngày)",
                        icon = Lucide.Dumbbell,
                        isSelected = uiState.weeklyRate == 0.5,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.5)) }
                    )
                }

                "improve_health" -> {
                    SelectionCard(
                        title = "Giảm nhẹ 0.25 kg/tuần",
                        description = "Cải thiện sức khỏe bằng giảm cân nhẹ (500 kcal/ngày)",
                        icon = Lucide.TrendingDown,
                        isSelected = uiState.weeklyRate == -0.25,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(-0.25)) }
                    )

                    SelectionCard(
                        title = "Giữ nguyên cân nặng",
                        description = "Duy trì và cải thiện sức khỏe (0 kcal/ngày)",
                        icon = Lucide.Activity,
                        isSelected = uiState.weeklyRate == 0.0,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.0)) }
                    )

                    SelectionCard(
                        title = "Tăng nhẹ 0.25 kg/tuần",
                        description = "Cải thiện sức khỏe bằng tăng cân nhẹ (500 kcal/ngày)",
                        icon = Lucide.TrendingUp,
                        isSelected = uiState.weeklyRate == 0.25,
                        onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeeklyRate(0.25)) }
                    )
                }

                else -> {
                    Text(
                        text = "Vui lòng chọn mục tiêu trước",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

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

