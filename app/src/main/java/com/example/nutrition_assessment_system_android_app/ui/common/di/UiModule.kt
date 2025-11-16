package com.example.nutrition_assessment_system_android_app.ui.common.di

import android.content.Context
import com.example.nutrition_assessment_system_android_app.ui.common.auth.GoogleSignInHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UiModule {

    @Provides
    @ViewModelScoped
    fun provideGoogleSignInHelper(
        @ApplicationContext context: Context
    ): GoogleSignInHelper = GoogleSignInHelper(context)
}