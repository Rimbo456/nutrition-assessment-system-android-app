package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.nutrition_assessment_system_android_app.domain.model.Message
import com.example.nutrition_assessment_system_android_app.domain.params.GetMessagesParams
import com.example.nutrition_assessment_system_android_app.domain.params.GetSessionsParams
import com.example.nutrition_assessment_system_android_app.domain.params.SendMessageParams
import com.example.nutrition_assessment_system_android_app.domain.usecase.chat.CreateSessionUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.chat.GetMessagesUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.chat.GetSessionsUseCase
import com.example.nutrition_assessment_system_android_app.domain.usecase.chat.SendMessageUseCase
import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val getSessionsUseCase: GetSessionsUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
): BaseViewModel<ChatIntent, ChatViewStates.ChatViewState, ChatViewStates.ChatViewModelState>(
    initState = ChatViewStates.ChatViewModelState()
) {
    override fun onTriggerIntent(intent: ChatIntent) {
        viewModelScope.launch {
            when (intent) {
                is ChatIntent.SendMessage -> {
                    val userMsg = intent.message
                    val userMsgObj = Message(
                        id = "",
                        sessionId = intent.sessionId,
                        role = "user",
                        text = userMsg,
                        createdAt = System.currentTimeMillis()
                    )
                    viewModelState.update {
                        it.copy(
                            messages = it.messages + userMsgObj,
                            isBotResponse = true
                        )
                    }
                    sendMessageUseCase.execute(
                        param = SendMessageParams(
                            sessionId = intent.sessionId,
                            message = intent.message
                        )
                    ).reduce(
                        onSuccess = { data ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    messages = it.messages + data,
                                    isBotResponse = false
                                )
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message
                                )
                            }
                        },
                        onLoading = {
                            viewModelState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    )
                }

                is ChatIntent.CreateNewChatSession -> {
                    createSessionUseCase.execute(
                        param = Unit
                    ).reduce(
                        onSuccess = { data ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    currentSession = data
                                )
                            }
                        },
                        onError = { message, error ->
                            viewModelState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = message
                                )
                            }
                        },
                        onLoading = {
                            viewModelState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    )
                }

                is ChatIntent.LoadChatSessionHistory -> {
                    getSessionsUseCase.execute(
                        param = GetSessionsParams(
                            forceRefresh = intent.forceRefresh
                        )
                    ).collect { result ->
                        result.reduce(
                            onSuccess = { data ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        allSessions = data
                                    )
                                }
                            },
                            onError = { message, error ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = message
                                    )
                                }
                            },
                            onLoading = {
                                viewModelState.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }
                            }
                        )
                    }
                }

                is ChatIntent.LoadChatMessagesHistory -> {
                    getMessagesUseCase.execute(
                        param = GetMessagesParams(
                            sessionId = intent.sessionId,
                            forceRefresh = intent.forceRefresh
                        )
                    ).collect { result ->
                        result.reduce(
                            onSuccess = { data ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        messages = data
                                    )
                                }
                            },
                            onError = { message, error ->
                                viewModelState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = message
                                    )
                                }
                            },
                            onLoading = {
                                viewModelState.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }
                            }
                        )
                    }
                }

                is ChatIntent.SelectChatSession -> {
                    viewModelState.update {
                        it.copy(
                            currentSession = intent.session
                        )
                    }
                }

                is ChatIntent.BackToChatSessionsList -> {
                    viewModelState.update {
                        it.copy(
                            currentSession = null,
                            messages = emptyList()
                        )
                    }
                }
            }
        }
    }
}