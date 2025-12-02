package com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.BasicCalculationUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.GetProfileUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.GetWeightLogsUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.InsertWeightLogUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.UpdateProfileUserUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val basicCalculationUseCase: BasicCalculationUseCase,
    private val insertWeightLogUseCase: InsertWeightLogUseCase,
    private val getWeightLogsUseCase: GetWeightLogsUseCase
): BaseViewModel<ProfileIntent, ProfileViewStates.ProfileViewState, ProfileViewStates.ProfileViewModelState>(
    initState = ProfileViewStates.ProfileViewModelState()
) {
    override fun onTriggerIntent(intent: ProfileIntent) {
        viewModelScope.launch {
            when (intent) {
                is ProfileIntent.GetUserProfile -> {
                    getProfileUseCase.execute(Unit).reduce(
                        onSuccess = { user ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    name = user.name,
                                    avatar = user.avatar,
                                    email = user.email,
                                    currentWeight = user.weight?.toDouble(),
                                    targetWeight = user.targetWeight,
                                    startWeight = user.startWeight,
                                    goal = user.goal,
                                    height = user.height?.toDouble(),
                                    weeklyRate = user.weeklyRate,
                                    age = user.age
                                )
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message
                                )
                            }
                        },
                        onLoading = {
                            viewModelState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    )
                }
                is ProfileIntent.GetBasicInfo -> {
                    basicCalculationUseCase.execute(Unit).reduce(
                        onSuccess = { data ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    targetWater = data.waterTarget,
                                    targetCalories = data.caloriesTarget,
                                    bmi = data.bmi,
                                    carbohydrateGoal = data.carbTarget,
                                    targetFat = data.fatTarget,
                                    targetProtein = data.proteinTarget,
                                )
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message
                                )
                            }
                        },
                        onLoading = {
                            viewModelState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    )
                }
                is ProfileIntent.UpdateWeight -> {
                    insertWeightLogUseCase.execute(intent.weight.toFloat()).reduce(
                        onSuccess = {
                            viewModelState.update {
                                it.copy(
                                    navigateToProfile = createOneTimeEvent(true) {
                                        viewModelState.update {
                                            it.copy(
                                                navigateToProfile = null
                                            )
                                        }
                                    }
                                )
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    errorMessage = message
                                )
                            }
                        },
                        onLoading = {
                            // No state update needed
                        }
                    )
                }
                is ProfileIntent.GetWeightLogs -> {
                    getWeightLogsUseCase.execute(false).collect {
                        it.reduce(
                            onSuccess = { weightLogs ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        weightLogs = weightLogs
                                    )
                                }
                            },
                            onError = { message, error ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = message
                                    )
                                }
                            },
                            onLoading = {
                                viewModelState.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}