package com.example.reciclapp_bolivia.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp_bolivia.BuildConfig
import com.example.reciclapp_bolivia.presentation.states.ClassifierState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ClassifierViewModel"

private const val PROMPT = """
Analiza la imagen y proporciona la siguiente información sobre el material reciclable:

🔍 **IDENTIFICACIÓN:**
- Tipo de material (plástico, papel, cartón, metal, vidrio, orgánico, etc.)
- Subtipo específico (PET, HDPE, aluminio, etc.) si es visible

📋 **CLASIFICACIÓN:**
- Categoría de reciclaje correspondiente
- Color del contenedor donde debe ir

♻️ **PREPARACIÓN:**
- Pasos para preparar el material antes del reciclaje
- Qué partes quitar o limpiar (etiquetas, tapas, residuos)

💡 **CONSEJOS PRÁCTICOS:**
- Alternativas de reutilización antes del reciclaje
- Errores comunes que evitar
- Consejos específicos

⚠️ **ADVERTENCIAS:**
- Si NO es reciclable, explica por qué
- Alternativas de disposición responsable

Sé conciso pero completo. Usa emojis para hacer la información más visual y fácil de seguir. Adapta los consejos al contexto de ayudar al planeta cuando sea relevante.
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