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
import androidx.navigation.NavController
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.NumberPicker
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingHeader
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.component.OnboardingProgressIndicator
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun WeightInputScreen(
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
                        onClick = { navController.navigate("onboarding/height") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quay lại")
                    }

                    Button(
                        onClick = { navController.navigate("onboarding/activity") },
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
                currentStep = 4,
                totalSteps = 8
            )

            OnboardingHeader(
                title = "Cân nặng hiện tại",
                subtitle = "Cân nặng giúp xác định BMI và lượng calo cần thiết mỗi ngày"
            )

            Spacer(modifier = Modifier.height(40.dp))

            NumberPicker(
                value = uiState.weight?.toInt() ?: 65,
                onValueChange = { viewModel.onTriggerIntent(OnBoardingIntent.SetWeight(it.toFloat())) },
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

