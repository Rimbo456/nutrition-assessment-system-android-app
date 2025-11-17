package com.example.nutrition_assessment_system_android_app.ui.feature.chat.viewmodel

import com.example.nutrition_assessment_system_android_app.ui.common.component.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): BaseViewModel<ChatIntent, ChatViewStates.ChatViewState, ChatViewStates.ChatViewModelState>(
    initState = ChatViewStates.ChatViewModelState()
) {
    override fun onTriggerIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> {
                // Handle sending message
            }
        }
    }
}