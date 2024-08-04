package com.example.reciclapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.entities.Material
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.comentario.GetComentariosUseCase
import com.example.reciclapp.domain.usecases.comentario.ListarComentariosDeCompradorUseCase
import com.example.reciclapp.domain.usecases.comprador.GetCompradorUseCase
import com.example.reciclapp.domain.usecases.material.ListarMaterialesPorCompradorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompradoresViewModel @Inject constructor(
    private val getCompradorUseCase: GetCompradorUseCase,
    private val listarMaterialesPorCompradorUseCase: ListarMaterialesPorCompradorUseCase,
    private val listarComentariosDeCompradorUseCase: ListarComentariosDeCompradorUseCase
) :
    ViewModel(
    ) {

    private val _selectedComprador = MutableStateFlow<Usuario?>(null)
    val selectedComprador: StateFlow<Usuario?> = _selectedComprador

    private val _materiales = MutableStateFlow<MutableList<Material>>(mutableListOf())
    val materiales: StateFlow<MutableList<Material>> = _materiales

    private val _comentarios = MutableStateFlow<MutableList<Comentario>>(mutableListOf())
    val comentarios: StateFlow<MutableList<Comentario>> = _comentarios

    fun fetchCompradorById(idComprador: Int) {
        viewModelScope.launch {
            _selectedComprador.value = getCompradorUseCase.execute(idComprador)
        }
    }
    fun fetchMaterialesByComprador(idComprador: Int) {
        viewModelScope.launch {
            _materiales.value = listarMaterialesPorCompradorUseCase.execute(idComprador)
        }
    }

    fun fetchComentariosByComprador(idComprador: Int) {
        viewModelScope.launch {
            _comentarios.value = listarComentariosDeCompradorUseCase.execute(idComprador)
        }
    }
}