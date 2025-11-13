package com.example.nutrition_assessment_system_android_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

typealias FeatureNavGraph = NavGraphBuilder.(NavHostController) -> Unit

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    featureNavGraphs: List<FeatureNavGraph> = emptyList()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        featureNavGraphs.forEach { graph ->
            graph(navController)
        }

        composable(Screen.Home.route) {
            // Home Screen Content
        }
    }
}