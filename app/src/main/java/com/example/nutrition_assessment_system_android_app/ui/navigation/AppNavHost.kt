package com.example.nutrition_assessment_system_android_app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrition_assessment_system_android_app.ui.common.component.screen.SplashScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.navigation.authNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate("auth/login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                navigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        authNavGraph(navController)

        composable("home") {
            // Tạm thời dùng placeholder, sau sẽ thay bằng HomeScreen thật
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Home Screen - Authenticated")
            }
        }
    }
}