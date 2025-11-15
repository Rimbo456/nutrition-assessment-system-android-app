package com.example.nutrition_assessment_system_android_app.domain.util

abstract class UseCase<in P, out R> {
    abstract suspend fun execute(param: P): R
}