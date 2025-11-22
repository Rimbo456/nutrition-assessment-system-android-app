package com.example.nutrition_assessment_system_android_app.ui.feature.overview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Activity
import com.composables.icons.lucide.Apple
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Droplets
import com.composables.icons.lucide.Flame
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Target
import com.composables.icons.lucide.Zap
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.DailyIntakeCard
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.StatisticsCard
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.StatisticsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp, bottom = 75.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CaloriCam",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Show Date Picker Dialog
                },
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Calendar,
                        contentDescription = "Select Date",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    Icon(
                        imageVector = Lucide.ChevronDown,
                        contentDescription = "Select Date",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Daily Calorie Intake
        DailyIntakeCard(
            title = "Daily Calories",
            percentage = "68%",
            progress = 1360,
            sum = 2000,
            icon = Lucide.Flame,
            iconBackgroundColor = MaterialTheme.colorScheme.error
        )

        // Water Intake
        DailyIntakeCard(
            title = "Water Intake",
            percentage = "75%",
            progress = 1800,
            sum = 2400,
            icon = Lucide.Droplets,
            iconBackgroundColor = MaterialTheme.colorScheme.tertiary
        )

        // Macronutrients Card
        StatisticsCard(
            title = "Macronutrients",
            icon = Lucide.Target,
            iconBackgroundColor = MaterialTheme.colorScheme.primary,
            items = listOf(
                StatisticsItem(
                    label = "Protein",
                    value = "45/60g",
                    percentage = "75%",
                    progress = 0.75f
                ),
                StatisticsItem(
                    label = "Carbs",
                    value = "120/200g",
                    percentage = "60%",
                    progress = 0.60f
                ),
                StatisticsItem(
                    label = "Fat",
                    value = "30/50g",
                    percentage = "60%",
                    progress = 0.60f
                )
            ),
            onClick = { /* Navigate to macronutrients detail */ }
        )

        // Micronutrients Card
        StatisticsCard(
            title = "Micronutrients",
            icon = Lucide.Apple,
            iconBackgroundColor = MaterialTheme.colorScheme.secondary,
            items = listOf(
                StatisticsItem(
                    label = "Vitamin C",
                    value = "67/90mg",
                    percentage = "74%",
                    progress = 0.74f
                ),
                StatisticsItem(
                    label = "Iron",
                    value = "12/18mg",
                    percentage = "67%",
                    progress = 0.67f
                ),
                StatisticsItem(
                    label = "Calcium",
                    value = "800/1000mg",
                    percentage = "80%",
                    progress = 0.80f
                )
            ),
            onClick = { /* Navigate to micronutrients detail */ }
        )

        // Activity Card
        StatisticsCard(
            title = "Physical Activity",
            icon = Lucide.Activity,
            iconBackgroundColor = MaterialTheme.colorScheme.tertiary,
            items = listOf(
                StatisticsItem(
                    label = "Steps",
                    value = "7,850/10,000",
                    percentage = "78%",
                    progress = 0.78f
                ),
                StatisticsItem(
                    label = "Active Minutes",
                    value = "22/30 min",
                    percentage = "73%",
                    progress = 0.73f
                )
            ),
            onClick = { /* Navigate to activity detail */ }
        )

        // Energy Balance Card
        StatisticsCard(
            title = "Energy Balance",
            icon = Lucide.Zap,
            iconBackgroundColor = MaterialTheme.colorScheme.primary,
            items = listOf(
                StatisticsItem(
                    label = "Calories In",
                    value = "1,360 kcal",
                    percentage = "68%",
                    progress = 0.68f
                ),
                StatisticsItem(
                    label = "Calories Burned",
                    value = "2,150 kcal",
                    percentage = "100%",
                    progress = 1.0f
                ),
                StatisticsItem(
                    label = "Net Balance",
                    value = "-790 kcal",
                    percentage = "Deficit",
                    progress = 0.4f
                )
            )
        )
    }
}