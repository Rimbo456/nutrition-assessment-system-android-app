package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen.*
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

/**
 * Onboarding navigation graph: each step is a distinct route but all share one OnBoardingViewModel
 * by scoping the ViewModel to the parent graph back stack entry.
 */
fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = "onboarding/gender",
        route = "onboarding"
    ) {
        composable("onboarding/gender") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            GenderSelectionScreen(
                viewModel,
                navController
            )
        }
        composable("onboarding/age") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            AgeInputScreen(viewModel, navController)
        }
        composable("onboarding/height") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            HeightInputScreen(viewModel, navController)
        }
        composable("onboarding/weight") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            WeightInputScreen(viewModel, navController)
        }
        composable("onboarding/activity") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            ActivityLevelScreen(viewModel, navController)
        }
        composable("onboarding/goal") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            GoalSelectionScreen(viewModel, navController)
        }

        composable("onboarding/target-weight") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            TargetWeightScreen(viewModel, navController)
        }

        composable("onboarding/weekly-rate") {
            val parentEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry("onboarding")
            }
            val viewModel: OnBoardingViewModel = hiltViewModel(parentEntry)
            WeeklyRateScreen(viewModel, navController)
        }
    }
}
