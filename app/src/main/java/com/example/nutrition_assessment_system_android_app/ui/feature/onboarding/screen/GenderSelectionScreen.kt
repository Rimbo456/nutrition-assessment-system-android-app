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
import com.composables.icons.lucide.UserRound
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.SelectionCard
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun GenderSelectionScreen(
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
                        enabled = uiState.isGenderValid,
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
                currentStep = OnBoardingStep.GENDER.ordinal,
                totalSteps = OnBoardingStep.entries.size
            )

            OnboardingHeader(
                title = "Giới tính của bạn",
                subtitle = "Thông tin này giúp chúng tôi tính toán chính xác nhu cầu dinh dưỡng"
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectionCard(
                title = "Nam",
                icon = Lucide.UserRound,
                isSelected = uiState.gender == "male",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGender("male")) }
            )

            SelectionCard(
                title = "Nữ",
                icon = Lucide.UserRound,
                isSelected = uiState.gender == "female",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGender("female")) }
            )

            SelectionCard(
                title = "Khác",
                icon = Lucide.UserRound,
                isSelected = uiState.gender == "other",
                onClick = { viewModel.onTriggerIntent(OnBoardingIntent.SetGender("other")) }
            )

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

