package com.example.nutrition_assessment_system_android_app.ui.feature.auth.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen.LoginScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    composable("auth/login") { backStackEntry ->
        LoginScreen(
            viewModel = hiltViewModel(backStackEntry),
            navController = navController
        )
    }

    composable("auth/register") { backStackEntry ->
        SignUpScreen(
            //viewModel = hiltViewModel(backStackEntry)
        )
    }
}