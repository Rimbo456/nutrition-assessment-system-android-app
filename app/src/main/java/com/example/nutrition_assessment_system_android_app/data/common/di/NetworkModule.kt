package com.example.nutrition_assessment_system_android_app.data.common.di

import android.content.Context
import com.example.nutrition_assessment_system_android_app.data.auth.FirebaseAuthHelper
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.remote.ChatApiService
import com.example.nutrition_assessment_system_android_app.ui.common.auth.GoogleSignInHelper
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.UserApiService
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.interceptor.ResponseLoggingInterceptor
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
//        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ResponseLoggingInterceptor())
//            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.2.105:3000/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthHelper(
        firebaseAuth: FirebaseAuth
    ): FirebaseAuthHelper = FirebaseAuthHelper(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChatApiService(retrofit: Retrofit): ChatApiService {
        return retrofit.create(ChatApiService::class.java)
    }
}