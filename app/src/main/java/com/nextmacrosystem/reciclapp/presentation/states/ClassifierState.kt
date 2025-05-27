package com.nextmacrosystem.reciclapp.presentation.states

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