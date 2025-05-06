package com.example.reciclapp.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.BuildConfig
import com.example.reciclapp.presentation.states.ClassifierState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ClassifierViewModel"
private const val PROMPT =
    "Que tipo de material reciclable es lo que esta en la imagen en la clasificacion de materiales reciclables?, responde siempre en espaniol, y da consejos de como reciclarlo que me ayuden, por favor siempre dame consejos utiles."

class ClassifierViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ClassifierState> =
        MutableStateFlow(ClassifierState.Initial)
    val uiState: StateFlow<ClassifierState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY
    )

    fun sendPrompt(
        bitmap: Bitmap
    ) {
        _uiState.value = ClassifierState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        image(bitmap)
                        text(PROMPT)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = ClassifierState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = ClassifierState.Error(e.localizedMessage ?: "")
            }
        }
    }
}