package com.example.nutrition_assessment_system_android_app.ui.feature.profile.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.interfaces.ViewIntent

sealed interface ProfileIntent: ViewIntent {
    object GetUserProfile: ProfileIntent
    object GetBasicInfo: ProfileIntent
}