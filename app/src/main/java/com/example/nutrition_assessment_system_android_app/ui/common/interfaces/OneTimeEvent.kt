package com.example.nutrition_assessment_system_android_app.ui.common.interfaces

interface OneTimeEvent<T> {
    val data: T
    val onConsumed: () -> Unit
}