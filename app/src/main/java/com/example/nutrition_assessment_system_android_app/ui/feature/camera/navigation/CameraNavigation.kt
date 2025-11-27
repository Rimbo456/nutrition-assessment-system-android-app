package com.example.nutrition_assessment_system_android_app.ui.feature.camera.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.screen.CameraScreen

fun NavGraphBuilder.cameraNavGraph(navController: NavHostController) {
    composable("camera") { backStackEntry ->
        CameraScreen(
            onClose = { navController.popBackStack() }
        )
    }
}