package com.example.nutrition_assessment_system_android_app.ui.feature.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.*
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel.ProfileIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel.ProfileViewModel
import kotlin.math.ceil

// Helper functions
fun calculateBMI(weight: Double, heightCm: Double): Double {
    val heightM = heightCm / 100
    return weight / (heightM * heightM)
}

fun getBMICategory(bmi: Double): Pair<String, Color> {
    return when {
        bmi < 18.5 -> "Thiếu cân" to Color(0xFF3B82F6)
        bmi < 25 -> "Bình thường" to Color(0xFF10B981)
        bmi < 30 -> "Thừa cân" to Color(0xFFF97316)
        else -> "Béo phì" to Color(0xFFEF4444)
    }
}

fun getGoalInfo(goalType: String): Pair<String, ImageVector> {
    return when (goalType.lowercase()) {
        "lose", "giảm cân" -> "Giảm cân" to Lucide.TrendingDown
        "gain", "tăng cân" -> "Tăng cân" to Lucide.TrendingUp
        else -> "Duy trì" to Lucide.Minus
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isWeightModalOpen by remember { mutableStateOf(false) }

    // Computed values
    val userHeight = uiState.height ?: 0.0
    val currentWeight = uiState.currentWeight ?: 0.0
    val targetWeight = uiState.targetWeight ?: 0.0
    val startWeight = uiState.startWeight ?: 0.0
    val bmi = calculateBMI(currentWeight, userHeight)
    val bmiCategory = getBMICategory(bmi)
    val goalInfo = getGoalInfo(uiState.goal ?: "Giảm cân")
    val weightDiff = currentWeight - targetWeight
    val weeklyWeightChangeGoal = uiState.weeklyRate ?: 0.0
    val weeksToGoal = (kotlin.math.abs(weightDiff) / weeklyWeightChangeGoal).let { ceil(it).toInt() }

    LaunchedEffect(Unit) {
        viewModel.onTriggerIntent(ProfileIntent.GetUserProfile)
        viewModel.onTriggerIntent(ProfileIntent.GetBasicInfo)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 75.dp)
    ) {
        // Header
        ProfileHeader(
            onNavigateToSetting = { TODO() }
        )

        // Profile Info Card
        ProfileInfoCard(
            name = uiState.name ?: "Người dùng",
            age = uiState.age ?: 0,
            userHeight = userHeight,
            currentWeight = currentWeight,
            bmi = bmi,
            bmiCategory = bmiCategory,
            onAvatarClick = { /* TODO: Change avatar */ },
            onEditClick = { /* TODO: Edit profile */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { isWeightModalOpen = true },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Lucide.Scale,
                    "Cập nhật cân nặng",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    "Cập nhật cân nặng",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Goal Card
        GoalCard(
            goalInfo = goalInfo,
            currentWeight = currentWeight,
            targetWeight = targetWeight,
            startWeight = startWeight,
            weightDiff = weightDiff,
            weeklyWeightChangeGoal = weeklyWeightChangeGoal,
            weeksToGoal = weeksToGoal,
            onUpdateWeightClick = { isWeightModalOpen = true }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Goals Card
        DailyGoalsCard(
            targetCalories = uiState.targetCalories?.toInt() ?: 0,
            protein = uiState.targetProtein?.toInt() ?: 0,
            carbs = uiState.carbohydrateGoal?.toInt() ?: 0,
            fat = uiState.targetFat?.toInt() ?: 0,
            water = uiState.targetWater?.toInt() ?: 0
        )

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Weight input modal is a stateless component; the screen handles state update
    if (isWeightModalOpen) {
        WeightInputDialogComposable(
            currentWeight = uiState.currentWeight ?: 0.0,
            onDismiss = { isWeightModalOpen = false },
            onConfirm = { newWeight ->
                viewModel.onTriggerIntent(ProfileIntent.UpdateWeight(newWeight))
                isWeightModalOpen = false
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
}