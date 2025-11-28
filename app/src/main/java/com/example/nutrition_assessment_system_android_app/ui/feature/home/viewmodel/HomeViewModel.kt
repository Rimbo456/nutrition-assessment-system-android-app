package com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(

): BaseViewModel<HomeIntent, HomeViewStates.HomeViewState, HomeViewStates.HomeViewModelState>(
    initState = HomeViewStates.HomeViewModelState()
) {
    override fun onTriggerIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}