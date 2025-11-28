package com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.GetProfileUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
): BaseViewModel<HomeIntent, HomeViewStates.HomeViewState, HomeViewStates.HomeViewModelState>(
    initState = HomeViewStates.HomeViewModelState()
) {
    override fun onTriggerIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.CheckInformationUser -> {
                    getProfileUseCase.execute(Unit).reduce(
                        onSuccess = { data ->
                            if (data.gender == null || data.age == null || data.weight == null || data.height == null) {
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        navigateToOnBoarding = createOneTimeEvent(true) {
                                            viewModelState.update { it.copy(navigateToOnBoarding = null) }
                                        }
                                    )
                                }
                            } else {
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        showLoginSuccessToast = createOneTimeEvent(true) {
                                            viewModelState.update { it.copy(showLoginSuccessToast = null) }
                                        }
                                    )
                                }
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}