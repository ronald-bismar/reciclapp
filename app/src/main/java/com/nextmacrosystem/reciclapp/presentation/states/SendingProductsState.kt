package com.nextmacrosystem.reciclapp.presentation.states

sealed class SendingProductsState {
    data object Loading : SendingProductsState()
    data object Success : SendingProductsState()
    data class Error(val error: String) : SendingProductsState()
    data object InitialState : SendingProductsState()
}