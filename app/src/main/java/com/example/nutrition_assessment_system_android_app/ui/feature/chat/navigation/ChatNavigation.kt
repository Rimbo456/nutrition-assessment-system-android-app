package com.example.nutrition_assessment_system_android_app.ui.feature.chat.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen.ChatScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel.ChatViewModel

fun NavGraphBuilder.chatNavGraph(navController: NavHostController) {
    composable(
        route = "chat"
    ) {
        val parentEntry = navController.getBackStackEntry("home")
        val sharedVm: ChatViewModel = hiltViewModel(parentEntry)
        ChatScreen(
            navController = navController,
            viewModel = sharedVm
        )
    }
}