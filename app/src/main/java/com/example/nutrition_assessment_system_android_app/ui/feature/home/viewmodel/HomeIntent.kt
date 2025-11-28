package com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface HomeIntent: ViewIntent {
    object CheckInformationUser : HomeIntent
}