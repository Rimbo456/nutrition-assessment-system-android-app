package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.params.LoginWithEmailAndPasswordParams
import com.example.nutrition_assessment_system_android_app.domain.params.LoginWithGoogleParams
import com.example.nutrition_assessment_system_android_app.domain.params.RegisterParams
import com.example.nutrition_assessment_system_android_app.domain.preferences.SharedPreferences
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.CheckAuthStatusUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.LoginWithEmailAndPasswordUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.LoginWithGoogleUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.ObserveAuthStateUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.RegisterUseCase
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.ui.common.auth.GoogleSignInHelper
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val googleSignInHelper: GoogleSignInHelper,
    private val registerUseCase: RegisterUseCase,
    private val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase,
    private val observeAuthStateUseCase: ObserveAuthStateUseCase,
) : BaseViewModel<AuthIntent, AuthViewStates.AuthViewState, AuthViewStates.AuthViewModelState>(
    initState = AuthViewStates.AuthViewModelState()
) {

    init {
        viewModelScope.launch {
            Log.d("AuthViewModel", "Init: Starting auth check")
            viewModelState.update {
                it.copy(isLoading = true)
            }
            checkAuthStatusUseCase().collect { result ->
                Log.d("AuthViewModel", "Init: Received result: $result")
                when (result) {
                    is Resource.Success -> {
                        Log.d("AuthViewModel", "Init: Success - isAuthenticated=${result.data}")
                        viewModelState.update {
                            it.copy(
                                isAuthenticated = result.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.d("AuthViewModel", "Auth check failed: ${result.message}")
                        viewModelState.update {
                            it.copy(
                                isAuthenticated = false,
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                    is Resource.Loading -> {
                        // Not used anymore
                    }
                }
            }
            Log.d("AuthViewModel", "Init: Auth check completed")
        }
//        viewModelScope.launch {
//            observeAuthStateUseCase().collect { isAuthenticated ->
//                Log.d("AuthViewModel", "Auth state changed: isAuthenticated=$isAuthenticated")
//                viewModelState.update {
//                    it.copy(
//                        isAuthenticated = isAuthenticated,
//                        isLoading = false
//                    )
//                }
//            }
//        }
    }

    override fun onTriggerIntent(intent: AuthIntent) {
        viewModelScope.launch {
            when (intent) {
                AuthIntent.NavigateToSignUpScreen -> {
                    viewModelState.update {
                        it.copy(
                            navigateToSignUpScreen = createOneTimeEvent(data = true) {
                                viewModelState.update { it.copy(navigateToSignUpScreen = null) }
                            }
                        )
                    }
                }

                is AuthIntent.Register -> {
                    handleRegister(intent)
                }

                is AuthIntent.LoginWithEmailAndPassword -> {
                    handleLoginWithEmailAndPassword(intent)
                }

                is AuthIntent.LoginWithGoogle -> {
                    handelLoginWithGoogle(intent)
                }

                AuthIntent.GetSignInIntent -> {
                    val signInIntent: Intent = googleSignInHelper.getSignInIntent()
                    viewModelState.update {
                        it.copy(
                            googleSignInIntent = createOneTimeEvent(data = signInIntent) {
                                viewModelState.update { it.copy(googleSignInIntent = null) }
                            }
                        )
                    }
                }

                AuthIntent.Logout -> {
                    // TODO: Implement logout logic
                }

                AuthIntent.GetAuthToken -> {
                    sharedPreferences.getAuthToken().collect { authToken ->
                        viewModelState.update {
                            it.copy(
                                authToken = authToken
                            )
                        }
                    }
                }
                is AuthIntent.SetAuthToken -> {
                    sharedPreferences.updateAuthToken(intent.token)
                }

                AuthIntent.CheckUserSession -> {

                }
            }
        }
    }

    private suspend fun handleRegister(intent: AuthIntent.Register) {
        if (intent.password != intent.confirmPassword) {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Passwords do not match"
                )
            }
            return
        }
        viewModelState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        registerUseCase.execute(
            RegisterParams(
                name = intent.name,
                email = intent.email,
                password = intent.password
            )
        ).reduce(
            onLoading = { data ->
                viewModelState.update {
                    it.copy(isLoading = true)
                }
            },
            onSuccess = { user ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        errorMessage = null,
                        registrationSuccess = createOneTimeEvent(data = user) {
                            viewModelState.update { it.copy(registrationSuccess = null) }
                        }
                    )
                }
            },
            onError = { message, _ ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
            }
        )
    }

    private suspend fun handelLoginWithGoogle(intent: AuthIntent.LoginWithGoogle) {
        viewModelState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        val googleToken = googleSignInHelper.extractTokenFromIntent(intent.data)

        if (googleToken == null) {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Google sign-in failed"
                )
            }
            return
        }

        loginWithGoogleUseCase.execute(
            LoginWithGoogleParams(
                googleToken = googleToken
            )
        ).reduce(
            onLoading = {
                viewModelState.update {
                    it.copy(isLoading = true)
                }
            },
            onSuccess = { user ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        errorMessage = null,
                        loginSuccess = createOneTimeEvent(data = user) {
                            viewModelState.update { it.copy(loginSuccess = null) }
                        }
                    )
                }
            },
            onError = { message, _ ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
            }
        )
    }

    private suspend fun handleLoginWithEmailAndPassword(intent: AuthIntent.LoginWithEmailAndPassword) {
        viewModelState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        if (intent.email.isBlank() || intent.password.isBlank()) {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Email and password must not be blank"
                )
            }
            return
        }
        loginWithEmailAndPasswordUseCase.execute(
            LoginWithEmailAndPasswordParams(
                email = intent.email,
                password = intent.password
            )
        ).reduce(
            onLoading = {
                viewModelState.update {
                    it.copy(isLoading = true)
                }
            },
            onSuccess = { user ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        errorMessage = null,
                        loginSuccess = createOneTimeEvent(data = user) {
                            viewModelState.update { it.copy(loginSuccess = null) }
                        }
                    )
                }
            },
            onError = { message, _ ->
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
            }
        )
    }
}