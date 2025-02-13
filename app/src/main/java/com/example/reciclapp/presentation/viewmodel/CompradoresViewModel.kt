package com.example.reciclapp.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.comentario.ListarComentariosDeCompradorUseCase
import com.example.reciclapp.domain.usecases.comprador.GetCompradorUseCase
import com.example.reciclapp.domain.usecases.producto.ListarMaterialesPorCompradorUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.vendedor.ComentarACompradorUseCase
import com.example.reciclapp.model.util.GenerateID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class CompradoresViewModel @Inject constructor(
    private val getCompradorUseCase: GetCompradorUseCase,
    private val listarMaterialesPorCompradorUseCase: ListarMaterialesPorCompradorUseCase,
    private val listarComentariosDeCompradorUseCase: ListarComentariosDeCompradorUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val comentarACompradorUseCase: ComentarACompradorUseCase,
) :
    ViewModel(
    ) {

    init {
        loadMyUserPreferences()
        Log.d("MyUser", "MyUser: $myUser")
    }

    private val _myUser = MutableLiveData(Usuario())
    private val myUser: LiveData<Usuario> get() = _myUser

    private val _selectedComprador = MutableLiveData(Usuario())
    val selectedComprador: LiveData<Usuario> = _selectedComprador

    private val _materiales = MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val materiales: StateFlow<MutableList<ProductoReciclable>> = _materiales

    private val _comentarios = MutableStateFlow<MutableList<Comentario>>(mutableListOf())
    val comentarios: StateFlow<MutableList<Comentario>> = _comentarios

    private val _stateNewComment = MutableLiveData<Result<Comentario>?>()
    val stateNewComment: LiveData<Result<Comentario>?> get() = _stateNewComment

    private fun loadMyUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _myUser.postValue(usuario)
        }
    }

    fun fetchCompradorById(idComprador: String) {
        viewModelScope.launch {
            _selectedComprador.value = getCompradorUseCase.execute(idComprador) ?: Usuario()
        }
    }

    fun fetchMaterialesByComprador(idComprador: String) {
        viewModelScope.launch {
            _materiales.value = listarMaterialesPorCompradorUseCase.execute(idComprador)
        }
    }

    fun fetchComentariosByComprador(idComprador: String) {
        viewModelScope.launch {
            _comentarios.value = listarComentariosDeCompradorUseCase.execute(idComprador)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarComentario(newComment: String, puntuacion: Int) {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDate = currentDate.format(formatter)

        // Verificar si `myUser` y `selectedComprador` no son nulos
        val myUserId = _myUser.value?.idUsuario
        val compradorId = _selectedComprador.value?.idUsuario

        if (myUserId != null && compradorId != null) {
            val newComentario = Comentario(
                idComentario = GenerateID(),
                idUsuarioQueComento = myUserId,
                nombreUsuarioQueComento = myUser.value!!.nombre,
                contenidoComentario = newComment,
                fechaComentario = formattedDate,
                puntuacion = puntuacion,
                idUsuarioComentado = compradorId
            )

            viewModelScope.launch {
                runCatching {
                    comentarACompradorUseCase.execute(newComentario)
                }.onSuccess {
                    _stateNewComment.value = Result.success(newComentario)
                    _comentarios.value.add(newComentario)
                }.onFailure { e ->
                    _stateNewComment.value = Result.failure(e)
                }
            }
        } else {
            // Manejar el caso donde `myUser` o `selectedComprador` son nulos
            _stateNewComment.value = Result.failure(IllegalStateException("Usuario o comprador no seleccionado."))
        }
    }

    fun resetState() {
        _stateNewComment.value = null
    }

}