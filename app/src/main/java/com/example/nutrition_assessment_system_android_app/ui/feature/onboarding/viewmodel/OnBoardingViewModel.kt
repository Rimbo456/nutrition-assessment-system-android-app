package com.example.nutrition_assessment_system_android_app.ui.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.params.UpdateUserProfileParams
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.UpdateProfileUserUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for onboarding flow following MVI pattern
 * Handles all user intents and updates state immutably
 */
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val updateProfileUserUseCase: UpdateProfileUserUseCase
) : BaseViewModel<OnBoardingIntent, OnBoardingStates.OnBoardingViewState, OnBoardingStates.OnBoardingViewModelState>(
    initState = OnBoardingStates.OnBoardingViewModelState()
) {
    override fun onTriggerIntent(intent: OnBoardingIntent) {
        viewModelScope.launch {
            when (intent) {
                is OnBoardingIntent.SetGender -> handleSetGender(intent.gender)
                is OnBoardingIntent.SetAge -> handleSetAge(intent.age)
                is OnBoardingIntent.SetHeight -> handleSetHeight(intent.height)
                is OnBoardingIntent.SetWeight -> handleSetWeight(intent.weight)
                is OnBoardingIntent.SetActivityLevel -> handleSetActivityLevel(intent.activityLevel)
                is OnBoardingIntent.SetGoal -> handleSetGoal(intent.goal)
                is OnBoardingIntent.SubmitOnboardingData -> handleCompleteOnBoarding()
            }
        }
    }

    // ==================== Data Input Handlers ====================

    private fun handleSetGender(gender: String) {
        viewModelState.update { current ->
            current.copy(
                gender = gender,
                isGenderValid = gender.isNotBlank(),
                errorMessage = null
            )
        }
    }

    private fun handleSetAge(age: Int) {
        val isValid = age in 10..120
        viewModelState.update { current ->
            current.copy(
                age = age,
                isAgeValid = isValid,
                errorMessage = if (!isValid) "Tuổi phải từ 10 đến 120" else null
            )
        }
    }

    private fun handleSetHeight(height: Int) {
        val isValid = height in 100..250
        viewModelState.update { current ->
            current.copy(
                height = height,
                isHeightValid = isValid,
                errorMessage = if (!isValid) "Chiều cao phải từ 100cm đến 250cm" else null
            )
        }
    }

    private fun handleSetWeight(weight: Float) {
        val isValid = weight in 30f..300f
        viewModelState.update { current ->
            current.copy(
                weight = weight,
                isWeightValid = isValid,
                errorMessage = if (!isValid) "Cân nặng phải từ 30kg đến 300kg" else null
            )
        }
    }

    private fun handleSetActivityLevel(activityLevel: String) {
        val validLevels = listOf("sedentary", "light", "moderate", "active", "very_active")
        val isValid = activityLevel in validLevels
        viewModelState.update { current ->
            current.copy(
                activityLevel = activityLevel,
                isActivityLevelValid = isValid,
                errorMessage = if (!isValid) "Mức độ hoạt động không hợp lệ" else null
            )
        }
    }

    private fun handleSetGoal(goal: String) {
        val validGoals = listOf(
            "lose_weight",
            "maintain_weight",
            "gain_weight",
            "build_muscle",
            "improve_health"
        )
        val isValid = goal in validGoals
        viewModelState.update { current ->
            current.copy(
                goal = goal,
                isGoalValid = isValid,
                errorMessage = if (!isValid) "Mục tiêu không hợp lệ" else null
            )
        }
    }

    // ==================== Completion Handler ====================

    private fun handleCompleteOnBoarding() {
        val current = viewModelState.value
        if (current.isGenderValid &&
            current.isAgeValid &&
            current.isHeightValid &&
            current.isWeightValid &&
            current.isActivityLevelValid &&
            current.isGoalValid)
        {
            viewModelState.update { it.copy(errorMessage = "Vui lòng hoàn thành tất cả các bước") }
            return
        }

        viewModelState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            updateProfileUserUseCase.execute(
                param = UpdateUserProfileParams(
                    gender = current.gender!!,
                    age = current.age!!,
                    height = current.height!!,
                    weight = current.weight!!,
                    activityLevel = current.activityLevel!!,
                    goal = current.goal!!
                )
            ).reduce(
                onSuccess = {
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                },
                onError = { message, _ ->
                    viewModelState.update {
                        it.copy(isLoading = false, errorMessage = message)
                    }
                },
                onLoading = {
                    viewModelState.update { it.copy(isLoading = true) }
                }
            )
        }

        // Temporary: Mark as complete without domain call
        viewModelState.update {
            it.copy(
                isLoading = false,
            )
        }
    }
}