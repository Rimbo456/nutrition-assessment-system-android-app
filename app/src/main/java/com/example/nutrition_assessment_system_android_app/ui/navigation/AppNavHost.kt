package com.example.nutrition_assessment_system_android_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.navigation.authNavGraph

typealias FeatureNavGraph = NavGraphBuilder.(NavHostController) -> Unit

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authNavGraph(navController)
    }
}