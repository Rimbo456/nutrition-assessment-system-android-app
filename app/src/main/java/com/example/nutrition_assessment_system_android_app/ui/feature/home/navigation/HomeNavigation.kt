package com.example.nutrition_assessment_system_android_app.ui.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.home.screen.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable("home") { backStackEntry ->
        HomeScreen()
    }
}