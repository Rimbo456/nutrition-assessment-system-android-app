package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.NumberPicker
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun HeightInputScreen(
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
                        enabled = uiState.isHeightValid,
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
                currentStep = OnBoardingStep.HEIGHT.ordinal,
                totalSteps = OnBoardingStep.entries.size
            )

            OnboardingHeader(
                title = "Chiều cao của bạn",
                subtitle = "Chiều cao là yếu tố quan trọng để tính BMI và nhu cầu năng lượng"
            )

            Spacer(modifier = Modifier.height(40.dp))

            NumberPicker(
                value = uiState.height ?: 170,
                onValueChange = { viewModel.onTriggerIntent(OnBoardingIntent.SetHeight(it)) },
                minValue = 100,
                maxValue = 250,
                unit = "cm",
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

