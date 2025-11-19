package com.example.nutrition_assessment_system_android_app.ui.feature.chat.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen.ChatScreen

fun NavGraphBuilder.chatNavGraph(navController: NavHostController) {
    composable("chat") { backStackEntry ->
        ChatScreen()
    }
}