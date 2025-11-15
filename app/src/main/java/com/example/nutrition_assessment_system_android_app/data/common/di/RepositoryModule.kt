package com.example.nutrition_assessment_system_android_app.data.common.di

import com.example.nutrition_assessment_system_android_app.data.common.datastore.UserSettingRepositoryImpl
import com.example.nutrition_assessment_system_android_app.data.user.repository.UserRepositoryImpl
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.repository.UserSettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserSettingRepository(
        userSettingRepositoryImpl: UserSettingRepositoryImpl
    ) : UserSettingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository

}