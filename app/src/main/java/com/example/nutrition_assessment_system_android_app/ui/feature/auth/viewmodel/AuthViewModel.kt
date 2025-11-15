package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.params.RegisterParams
import com.example.nutrition_assessment_system_android_app.domain.usecase.auth.RegisterUseCase
import com.example.nutrition_assessment_system_android_app.ui.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<AuthIntent, AuthViewStates.AuthViewState, AuthViewStates.AuthViewModelState>(
    initState = AuthViewStates.AuthViewModelState()
) {
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

                is AuthIntent.Login -> {
                    // TODO: Implement login logic
                }

                AuthIntent.Logout -> {
                    // TODO: Implement logout logic
                }
            }
        }
    }

    private suspend fun handleRegister(intent: AuthIntent.Register) {
        // Validate confirm password
        if (intent.password != intent.confirmPassword) {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Passwords do not match"
                )
            }
            return
        }

        // Set loading state
        viewModelState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        // Execute use case
        val result = registerUseCase.execute(
            RegisterParams(
                name = intent.name,
                email = intent.email,
                password = intent.password
            )
        )

        // Handle result using reduce helper
        result.reduce(
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
}