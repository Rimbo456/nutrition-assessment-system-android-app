package com.example.nutrition_assessment_system_android_app.ui.feature.overview.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
            .padding(bottom = 75.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        // Header Section with modern styling
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(horizontal = 20.dp)
                .padding(top = 28.dp, bottom = 24.dp)
        ) {
            // App Title with refined typography
            Text(
                text = "CaloriCam",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Date Picker Button - Modern minimal style
            Button(
                onClick = {
                    // Show Date Picker Dialog
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Calendar,
                        contentDescription = "Select Date",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Icon(
                        imageVector = Lucide.ChevronDown,
                        contentDescription = "Select Date",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Main Content Area with refined spacing
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Daily Calorie Intake Card
            DailyIntakeCard(
                title = "Daily Calories",
                percentage = "68%",
                progress = 1360,
                sum = 2000,
                icon = Lucide.Flame,
                iconBackgroundColor = MaterialTheme.colorScheme.error
            )

            // Water Intake Card
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

            // Physical Activity Card
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
}