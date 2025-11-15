package com.example.nutrition_assessment_system_android_app.ui.feature.auth.navigation

import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen.LoginScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen.SignUpScreen
import com.example.nutrition_assessment_system_android_app.ui.navigation.FeatureNavGraph

val authNavGraph: FeatureNavGraph = {
    composable("auth/login") { backStackEntry ->
        LoginScreen(
            //viewModel = hiltViewModel(backStackEntry)
        )
    }

    composable("auth/register") { backStackEntry ->
        SignUpScreen(
            //viewModel = hiltViewModel(backStackEntry)
        )
    }
}