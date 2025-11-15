package com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.ui.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

) : BaseViewModel<AuthIntent, AuthViewStates.AuthViewState, AuthViewStates.AuthViewModelState>(
    initState = AuthViewStates.AuthViewModelState()
) {
    override fun onTriggerIntent(intent: AuthIntent) {
        viewModelScope.launch {
            when (intent) {

                AuthIntent.NavigateToSignUpScreen -> {
                    viewModelState.update { it ->
                        it.copy(
                            navigateToSignUpScreen = createOneTimeEvent(data = true) {
                                viewModelState.update { it.copy(navigateToSignUpScreen = null) }
                            }
                        )
                    }
                }

                is AuthIntent.Login -> TODO()
                AuthIntent.Logout -> TODO()
                is AuthIntent.Register -> TODO()
            }
        }
    }
}