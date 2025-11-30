package com.example.nutrition_assessment_system_android_app.ui.feature.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.DailyCaloriesCard
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.QuickStatsCard
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.WaterActivityCard
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.WeightInputDialogComposable
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.WeightJourneyCard
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.component.WeightProgressCard
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel.ProfileIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel.ProfileViewModel

data class UserData(
    val name: String = "Nguyễn Văn An",
    val avatarUrl: String = "https://example.com/avatar.png",
    val goal: String = "Giảm cân",
    val dailyCalories: Int = 1800,
    val startWeight: Double = 85.0,
    val currentWeight: Double = 78.5,
    val targetWeight: Double = 70.0,
    val waterIntake: Int = 6,
    val waterGoal: Int = 8,
    val streak: Int = 15,
    val steps: Int = 5234,
    val bmi: Double = 24.5,
    val protein: Int = 85,
    val carbs: Int = 180
)

@Composable
fun ProfileScreen(
    userData: UserData = UserData(),
    viewModel: ProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isWeightModalOpen by remember { mutableStateOf(false) }
    var currentWeight by remember { mutableStateOf(userData.currentWeight) }

    val weightLost = userData.startWeight - currentWeight
    val weightRemaining = currentWeight - userData.targetWeight
    val progressPercentage =
        ((userData.startWeight - currentWeight) / (userData.startWeight - userData.targetWeight)) * 100

    LaunchedEffect(uiState) {
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
        // Top row: setting action (stateless visual)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hồ sơ",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            IconButton(
                onClick = { /* TODO: Navigate to settings screen */ }
            ) {
                Icon(
                    Lucide.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Hero section: avatar + basic info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)
            ) {
                AsyncImage(
                    model = userData.avatarUrl,
                    contentDescription = userData.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = uiState.name ?: "Unknown",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = uiState.goal ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "🔥 ${userData.streak} ngày liên tiếp",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Delegate main parts to components. All callbacks (update weight) routed here.
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            WeightProgressCard(
                currentWeight = uiState.currentWeight ?: 0.0,
                weightLost = weightLost,
                weightRemaining = weightRemaining,
                progressPercentage = progressPercentage,
                onUpdateClick = { isWeightModalOpen = true }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            DailyCaloriesCard(dailyCalories = userData.dailyCalories)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            WaterActivityCard(
                waterIntake = userData.waterIntake,
                waterGoal = userData.waterGoal,
                steps = userData.steps
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Quá trình cân nặng",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            WeightJourneyCard()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Thống kê chi tiết",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            QuickStatsCard()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Weight input modal is a stateless component; the screen handles state update
    if (isWeightModalOpen) {
        WeightInputDialogComposable(
            currentWeight = currentWeight,
            onDismiss = { isWeightModalOpen = false },
            onConfirm = { newWeight ->
                currentWeight = newWeight
                isWeightModalOpen = false
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
}