package com.example.nutrition_assessment_system_android_app.ui.feature.overview.screen

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.icons.lucide.Apple
import com.composables.icons.lucide.Coffee
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.UtensilsCrossed
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.DateNavigationBar
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.DailySummaryCard
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.FoodItem
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.MealCard
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.component.QuickActionsBar
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel.OverviewIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel.OverviewViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    // State for UI interactions
    var searchQuery by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {
        viewModel.onTriggerIntent(OverviewIntent.GetBasicInfo)
        viewModel.onTriggerIntent(
            OverviewIntent.GetMealsByDate(
                selectedDate.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                )
            )
        )
    }

    LaunchedEffect(uiState) {
        Log.d("OverviewScreen", "UI State: $uiState")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 80.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Date Navigation Bar
        DateNavigationBar(
            date = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            onPrevDay = {
                selectedDate = selectedDate.minusDays(1)
                viewModel.onTriggerIntent(
                    OverviewIntent.GetMealsByDate(
                        selectedDate.format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        )
                    )
                )
            },
            onNextDay = {
                val today = LocalDate.now()
                if (selectedDate.isBefore(today)) {
                    selectedDate = selectedDate.plusDays(1)
                }
                viewModel.onTriggerIntent(
                    OverviewIntent.GetMealsByDate(
                        selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    )
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Daily Summary Card
        DailySummaryCard(
            caloriesConsumed = uiState.totalCalories ?: 0,
            caloriesGoal = uiState.caloriesTarget?.toInt() ?: 2000,
            protein = uiState.totalProtein ?: 0,
            carbs = uiState.totalCarb ?: 0,
            fat = uiState.totalFat ?: 0,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Quick Actions Bar
        QuickActionsBar(
            searchQuery = searchQuery,
            onSearchChange = { searchQuery = it },
            onCameraClick = {
                // TODO: Open camera
            },
            onAddFoodClick = {
                // TODO: Open add food dialog
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Meal Cards
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Breakfast
            MealCard(
                title = "Bữa sáng",
                icon = Lucide.Coffee,
                items = uiState.breakfastItems,
                onAddFood = {
                    // TODO: Add food to breakfast
                },
                onEditFood = { foodItem ->
                    // TODO: Edit food item
                },
                onDeleteFood = { foodItem ->
                    // TODO: Delete food item
                }
            )

            // Lunch
            MealCard(
                title = "Bữa trưa",
                icon = Lucide.UtensilsCrossed,
                items = uiState.lunchItems,
                onAddFood = {
                    // TODO: Add food to lunch
                },
                onEditFood = { foodItem ->
                    // TODO: Edit food item
                },
                onDeleteFood = { foodItem ->
                    // TODO: Delete food item
                }
            )

            // Dinner
            MealCard(
                title = "Bữa tối",
                icon = Lucide.Moon,
                items = uiState.dinnerItems,
                onAddFood = {
                    // TODO: Add food to dinner
                },
                onEditFood = { foodItem ->
                    // TODO: Edit food item
                },
                onDeleteFood = { foodItem ->
                    // TODO: Delete food item
                }
            )

            // Snacks
            MealCard(
                title = "Ăn vặt",
                icon = Lucide.Apple,
                items = uiState.snackItems,
                onAddFood = {
                    // TODO: Add food to snacks
                },
                onEditFood = { foodItem ->
                    // TODO: Edit food item
                },
                onDeleteFood = { foodItem ->
                    // TODO: Delete food item
                }
            )
        }
    }
}