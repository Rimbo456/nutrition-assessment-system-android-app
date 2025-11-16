package com.example.nutrition_assessment_system_android_app.data.user.repository

import android.util.Log
import com.example.nutrition_assessment_system_android_app.data.auth.FirebaseAuthHelper
import com.example.nutrition_assessment_system_android_app.data.common.util.ApiHelper
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.UserApiService
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request.LoginRequest
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request.RegisterRequest
import com.example.nutrition_assessment_system_android_app.data.user.mapper.toUser
import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.repository.PreferencesRepository
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val firebaseAuthHelper: FirebaseAuthHelper,
    private val preferencesRepository: PreferencesRepository,
) : UserRepository {

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Resource<User> {
        return ApiHelper.safeApiCall(
            apiCall = {
                userApiService.registerUser(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )
            },
            transform = { response ->
                response.user.toUser()
            }
        )
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<User> {

        val idTokenResult = firebaseAuthHelper.getIdToken(email, password)
        if (idTokenResult is Resource.Error) {
            return Resource.Error(idTokenResult.message)
        }

        val idToken = (idTokenResult as Resource.Success).data

        return ApiHelper.safeApiCall(
            apiCall = {
                userApiService.loginUser(
                    LoginRequest(
                        idToken = idToken
                    )
                )
            },
            transform = { response ->
                response.user.toUser()
            }
        )
    }

    override suspend fun loginWithGoogle(
        googleToken: String
    ): Resource<User> {

        val idTokenResult = firebaseAuthHelper.getGoogleIdToken(googleToken)
        if (idTokenResult is Resource.Error) {
            return Resource.Error(idTokenResult.message)
        }

        val firebaseIdToken = (idTokenResult as Resource.Success).data

        return ApiHelper.safeApiCall(
            apiCall = {
                userApiService.loginUser(
                    LoginRequest(
                        idToken = firebaseIdToken
                    )
                )
            },
            transform = { response ->
                response.user.toUser()
            }
        )
    }

    override suspend fun checkAuthStatus(): Resource<Boolean> {
        return try {
            // Sử dụng first() để lấy auth state đầu tiên từ Flow
            // Với timeout 5 giây để tránh hanging vô hạn
            val isAuthenticated = withTimeoutOrNull(5000L) {
                firebaseAuthHelper.observeAuthState().first()
            } ?: return Resource.Success(false)

            if (isAuthenticated) {
                when (val tokenResult = firebaseAuthHelper.getCurrentUserIdToken()) {
                    is Resource.Success -> {
                        tokenResult.data?.let { token ->
                            preferencesRepository.updateAuthToken(token)
                        }
                    }
                    else -> {}
                }
            } else {
                preferencesRepository.clearAuthToken()
            }

            Resource.Success(isAuthenticated)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to check auth status")
        }
    }

    override fun observeAuthState(): Flow<Boolean> {
        return firebaseAuthHelper.observeAuthState()
    }
}