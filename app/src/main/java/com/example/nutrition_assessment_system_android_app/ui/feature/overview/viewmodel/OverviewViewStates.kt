package com.example.nutrition_assessment_system_android_app.ui.feature.overview.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewState

class OverviewViewStates {
    data class OverviewViewState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val caloriesTarget: Double? = null,
        val proteinTarget: Double? = null,
        val fatTarget: Double? = null,
        val carbTarget: Double? = null,
        val waterTarget: Double? = null,
    ): ViewState()

    data class OverviewViewModelState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val caloriesTarget: Double? = null,
        val proteinTarget: Double? = null,
        val fatTarget: Double? = null,
        val carbTarget: Double? = null,
        val waterTarget: Double? = null,
    ): ViewModelState() {
        override fun toUiState(): ViewState {
            return OverviewViewState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                caloriesTarget = caloriesTarget,
                proteinTarget = proteinTarget,
                fatTarget = fatTarget,
                carbTarget = carbTarget,
                waterTarget = waterTarget,
            )
        }
    }
}