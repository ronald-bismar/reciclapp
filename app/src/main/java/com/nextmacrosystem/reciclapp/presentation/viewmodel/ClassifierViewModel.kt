package com.nextmacrosystem.reciclapp.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.nextmacrosystem.reciclapp.presentation.states.ClassifierState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.nextmacrosystem.reciclapp.BuildConfig

private const val TAG = "ClassifierViewModel"

private const val PROMPT = """
    
Estoy desarrollando una app que ayuda a los usuarios a clasificar residuos reciclables mediante imágenes.

Petición:
Analiza la imagen proporcionada y dime qué tipo de material reciclable contiene.

Formato de respuesta deseado (en puntos):

Tipo de material reciclable

Descripción breve del material

Cómo clasificarlo correctamente

Consejo útil adicional para el usuario

Tono:
Educativo, claro y breve.

Condiciones especiales:
Si no estás seguro del tipo de material, indícalo y sugiere consultar con un centro de reciclaje.
Hazlo en tercera persona.
que las respuestas vayan al punto  y no tenga mucha redundancia.
Solo enfócate en los objetos reciclables y no en los no reciclables.


"""
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