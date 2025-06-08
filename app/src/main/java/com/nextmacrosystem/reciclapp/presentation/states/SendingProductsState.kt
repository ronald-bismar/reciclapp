<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/states/SendingProductsState.kt
package com.example.reciclapp_bolivia.presentation.states
========
package com.nextmacrosystem.reciclapp.presentation.states
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/states/SendingProductsState.kt

sealed class SendingProductsState {
    data object Loading : SendingProductsState()
    data object Success : SendingProductsState()
    data class Error(val error: String) : SendingProductsState()
    data object InitialState : SendingProductsState()
}