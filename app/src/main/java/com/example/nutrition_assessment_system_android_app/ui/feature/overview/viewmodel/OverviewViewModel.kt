package com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.usecase.user.BasicCalculationUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val basicCalculationUseCase: BasicCalculationUseCase
) : BaseViewModel<OverviewIntent, OverviewViewStates.OverviewViewState, OverviewViewStates.OverviewViewModelState>(
    initState = OverviewViewStates.OverviewViewModelState()
) {
    override fun onTriggerIntent(intent: OverviewIntent) {
        viewModelScope.launch {

        }
    }

    fun basicCalculation() {
        viewModelScope.launch {
            basicCalculationUseCase.execute(Unit).reduce(
                onSuccess = { data ->
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            caloriesTarget = data["CaloriesTarget"],
                            proteinTarget = data["ProteinTarget"],
                            fatTarget = data["FatTarget"],
                            carbTarget = data["CarbTarget"],
                            waterTarget = data["WaterTarget"]
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
                }
            )
        }
    }
}