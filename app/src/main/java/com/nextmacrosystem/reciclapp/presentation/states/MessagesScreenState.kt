package com.nextmacrosystem.reciclapp.presentation.states

sealed class MessagesScreenState {
    data object Loading : MessagesScreenState()
    data object Success : MessagesScreenState()
    data class Error(val error: String) : MessagesScreenState()
    data object Empty : MessagesScreenState()
}