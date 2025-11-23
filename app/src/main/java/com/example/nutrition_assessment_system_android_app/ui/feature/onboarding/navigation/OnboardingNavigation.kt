package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.enums.OnBoardingStep
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.screen.*
import com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel.OnBoardingViewModel

/**
 * Onboarding navigation graph - Simple version
 * All logic handled by ViewModel, navigation just passes shared instance
 */
fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = OnBoardingStep.GENDER.route,
        route = "onboarding"
    ) {
        OnBoardingStep.entries.forEach { step ->
            composable(step.route) {
                val parent = navController.getBackStackEntry("onboarding")
                val viewModel: OnBoardingViewModel = hiltViewModel(parent)
                when (step) {
                    OnBoardingStep.GENDER -> GenderSelectionScreen(viewModel)
                    OnBoardingStep.AGE -> AgeInputScreen(viewModel)
                    OnBoardingStep.HEIGHT -> HeightInputScreen(viewModel)
                    OnBoardingStep.WEIGHT -> WeightInputScreen(viewModel)
                    OnBoardingStep.ACTIVITY -> ActivityLevelScreen(viewModel)
                    OnBoardingStep.GOAL -> GoalSelectionScreen(viewModel)
                }
            }
        }
//        composable("onboarding/gender") {
//            val parent = navController.getBackStackEntry("onboarding")
//            GenderSelectionScreen(
//                viewModel = hiltViewModel(parent)
//            )
//        }
//
//        composable("onboarding/age") {
//            val parent = navController.getBackStackEntry("onboarding")
//            AgeInputScreen(
//                viewModel = hiltViewModel(parent)
//            )
//        }
//
//        composable("onboarding/height") {
//            val parent = navController.getBackStackEntry("onboarding")
//            HeightInputScreen(viewModel = hiltViewModel(parent))
//        }
//
//        composable("onboarding/weight") {
//            val parent = navController.getBackStackEntry("onboarding")
//            WeightInputScreen(viewModel = hiltViewModel(parent))
//        }
//
//        composable("onboarding/activity") {
//            val parent = navController.getBackStackEntry("onboarding")
//            ActivityLevelScreen(viewModel = hiltViewModel(parent))
//        }
//
//        composable("onboarding/goal") {
//            val parent = navController.getBackStackEntry("onboarding")
//            GoalSelectionScreen(viewModel = hiltViewModel(parent))
//        }
    }
}
