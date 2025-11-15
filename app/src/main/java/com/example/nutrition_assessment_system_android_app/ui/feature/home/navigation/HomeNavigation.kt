package com.example.nutrition_assessment_system_android_app.ui.feature.home.navigation

import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.home.screen.HomeScreen
import com.example.nutrition_assessment_system_android_app.ui.navigation.FeatureNavGraph

val homeNavGraph: FeatureNavGraph = {
    composable("home") { backStackEntry ->
        HomeScreen()
    }
}