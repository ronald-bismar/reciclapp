<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/states/ClassifierState.kt
package com.example.reciclapp_bolivia.presentation.states
========
package com.nextmacrosystem.reciclapp.presentation.states
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/states/ClassifierState.kt

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed interface ClassifierState {

    /**
     * Empty state when the screen is first shown
     */
    object Initial : ClassifierState

    /**
     * Still loading
     */
    object Loading : ClassifierState

    /**
     * Text has been generated
     */
    data class Success(val outputText: String) : ClassifierState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : ClassifierState
}