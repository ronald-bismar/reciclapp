<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/states/ChatScreenState.kt
package com.example.reciclapp_bolivia.presentation.states
========
package com.nextmacrosystem.reciclapp.presentation.states
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/states/ChatScreenState.kt

import android.app.Notification.MessagingStyle.Message

sealed class ChatScreenState {
    data object LoadingMessages : ChatScreenState()
    data class SendMessage(val message: Message) : ChatScreenState()
    data class ReceiveMessages(val messages: List<Message>) : ChatScreenState()
    data class Error(val error: String) : ChatScreenState()
    data object InitialState : ChatScreenState()
}