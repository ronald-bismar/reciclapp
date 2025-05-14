package com.example.reciclapp_bolivia.presentation.states

sealed class SendingProductsState {
    data object Loading : SendingProductsState()
    data object Success : SendingProductsState()
    data class Error(val error: String) : SendingProductsState()
    data object InitialState : SendingProductsState()
}