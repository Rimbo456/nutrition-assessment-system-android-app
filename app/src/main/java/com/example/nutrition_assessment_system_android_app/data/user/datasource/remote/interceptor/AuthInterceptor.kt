package com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.interceptor

import com.example.nutrition_assessment_system_android_app.data.auth.FirebaseAuthHelper
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val firebaseAuthHelper: FirebaseAuthHelper
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val tokenResult = runBlocking { firebaseAuthHelper.getCurrentUserIdToken() }
        val token = (tokenResult as? Resource.Success)?.data

        val requestWithAuth = if (!token.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else original

        return chain.proceed(requestWithAuth)
    }
}