package com.example.reciclapp_bolivia.presentation.states

import android.app.Notification.MessagingStyle.Message

sealed class ChatScreenState {
    data object LoadingMessages : ChatScreenState()
    data class SendMessage(val message: Message) : ChatScreenState()
    data class ReceiveMessages(val messages: List<Message>) : ChatScreenState()
    data class Error(val error: String) : ChatScreenState()
    data object InitialState : ChatScreenState()
}