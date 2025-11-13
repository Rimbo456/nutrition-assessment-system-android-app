package com.example.nutrition_assessment_system_android_app.ui.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.example.nutrition_assessment_system_android_app.ui.interfaces.OneTimeEvent
import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewIntent
import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewModelState
import com.example.nutrition_assessment_system_android_app.ui.interfaces.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel<VI : ViewIntent, VS : ViewState, VMS : ViewModelState>(
    initState: VMS
) : ViewModel() {

    protected val viewModelState = MutableStateFlow(initState)

    // UI state exposed to the UI
    val uiState: StateFlow<VS> = viewModelState
        .map { it.toUiState() as VS }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState() as VS
        )


    abstract fun onTriggerIntent(intent: VI)

    protected fun <T> createOneTimeEvent(
        data: T,
        onConsumed: () -> Unit
    ): OneTimeEvent<T> {
        return object : OneTimeEvent<T> {
            override val data: T = data
            override val onConsumed: () -> Unit = onConsumed
        }
    }

    protected fun <T> Resource<T>.reduce(
        onLoading: ((T?) -> Unit)? = null,
        onSuccess: (T) -> Unit,
        onError: (String, T?) -> Unit
    ) {
        when (this) {
            is Resource.Loading -> onLoading?.invoke(this.data)
            is Resource.Success -> onSuccess(this.data)
            is Resource.Error -> onError(this.message, this.data)
        }
    }
}