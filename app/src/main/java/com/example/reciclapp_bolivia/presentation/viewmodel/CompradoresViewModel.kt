package com.example.reciclapp_bolivia.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.usecases.comentario.ListarComentariosDeCompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.comprador.GetCompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.comprador.GetCompradoresUseCase
import com.example.reciclapp_bolivia.domain.usecases.producto.ActualizarProductoUseCase
import com.example.reciclapp_bolivia.domain.usecases.producto.CalcularCO2AhorradoEnKilos
import com.example.reciclapp_bolivia.domain.usecases.producto.ListarProductosPorCompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.producto.ObtenerProductosPredeterminados
import com.example.reciclapp_bolivia.domain.usecases.producto.RegistrarProductoUseCase
import com.example.reciclapp_bolivia.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp_bolivia.domain.usecases.vendedor.ComentarACompradorUseCase
import com.example.reciclapp_bolivia.util.GenerateID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class CompradoresViewModel @Inject constructor(
    private val getCompradorUseCase: GetCompradorUseCase,
    private val listarProductosPorCompradorUseCase: ListarProductosPorCompradorUseCase,
    private val listarComentariosDeCompradorUseCase: ListarComentariosDeCompradorUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val comentarACompradorUseCase: ComentarACompradorUseCase,
    private val obtenerProductosPredeterminados: ObtenerProductosPredeterminados,
    private val actualizarProductoUseCase: ActualizarProductoUseCase,
    private val registrarProductoUseCase: RegistrarProductoUseCase,
    private val calcularCO2AhorradoEnKilos: CalcularCO2AhorradoEnKilos,
    private val getCompradoresUseCase: GetCompradoresUseCase
) :
    ViewModel(
    ) {

    private val _showToast = MutableSharedFlow<String>()
    val showToast: SharedFlow<String> = _showToast

    init {
        loadMyUserPreferences()
    }

    private val _myUser = MutableLiveData(Usuario())
    val myUser: LiveData<Usuario> get() = _myUser

    private val _selectedComprador = MutableLiveData(Usuario())
    val selectedComprador: LiveData<Usuario> = _selectedComprador

    private val _productos = MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productos: StateFlow<MutableList<ProductoReciclable>> = _productos

    private val _comentarios = MutableStateFlow<MutableList<Comentario>>(mutableListOf())
    val comentarios: StateFlow<MutableList<Comentario>> = _comentarios

    private val _stateNewComment = MutableLiveData<Result<Comentario>?>()
    val stateNewComment: LiveData<Result<Comentario>?> get() = _stateNewComment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _productosPredeterminados =
        MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productosPredeterminados: StateFlow<MutableList<ProductoReciclable>> =
        _productosPredeterminados

    private val _productToUpdate = MutableStateFlow<ProductoReciclable?>(null)
    val productToUpdate: StateFlow<ProductoReciclable?> = _productToUpdate

    private val _co2AhorradoEnKilos = MutableStateFlow(0.0)
    val co2AhorradoEnKilos: StateFlow<Double> = _co2AhorradoEnKilos

    private val _productosActivos = MutableStateFlow(0)
    val productosActivos: StateFlow<Int> = _productosActivos

    private val _compradores = MutableStateFlow<List<Usuario>>(emptyList())
    val compradores: StateFlow<List<Usuario>> = _compradores


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
            _productos.value = listarProductosPorCompradorUseCase.execute(idComprador)
        }
    }

    fun getProductosVendidos(): List<ProductoReciclable>{
        return _productos.value.filter { producto -> producto.fueVendida && producto.idVendedor != ""}
    }

    fun fetchComentariosByComprador(idComprador: String) {
        viewModelScope.launch {
            _comentarios.value = listarComentariosDeCompradorUseCase.execute(idComprador)
        }
    }

    fun obtenerProductosPredeterminados() {
        viewModelScope.launch {
            _productosPredeterminados.value = obtenerProductosPredeterminados.execute()
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
            _stateNewComment.value =
                Result.failure(IllegalStateException("Usuario o comprador no seleccionado."))
        }
    }

    fun registrarNuevoProducto(
        productoReciclable: ProductoReciclable
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {

                // Registrar el producto
                registrarProductoUseCase.execute(productoReciclable)

                // Mostrar mensaje de éxito
                _showToast.emit("Producto registrado correctamente")
            } catch (e: Exception) {
                // Manejar errores
                Log.e("VendedoresViewModel", "Error al registrar el producto", e)
                _showToast.emit("Error al registrar el producto")
            } finally {
                // Finalizar el estado de carga
                _isLoading.value = false
            }
        }
    }

    fun actualizarProducto(
        productoReciclable: ProductoReciclable
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {

                // Ejecutar la actualización del producto
                actualizarProductoUseCase.execute(productoReciclable)
                fetchProductosByComprador(productoReciclable.idVendedor)

                // Mostrar mensaje de éxito
                _showToast.emit("Producto actualizado correctamente")
            } catch (e: Exception) {
                // Manejar errores
                Log.e("VendedoresViewModel", "Error al actualizar el producto", e)
                _showToast.emit("Error al actualizar el producto")
            } finally {
                // Finalizar el estado de carga
                _isLoading.value = false
            }
        }
    }

    fun fetchProductosByComprador(userId: String) {
        viewModelScope.launch {
            _productos.value = listarProductosPorCompradorUseCase.execute(userId)
            calcularCO2AhorradoEnKilos()
        }
    }

    private fun calcularCO2AhorradoEnKilos() {
        viewModelScope.launch {
            val productosReciclables = _productos.value
            _co2AhorradoEnKilos.value = calcularCO2AhorradoEnKilos.execute(productosReciclables)
        }
    }

    fun resetState() {
        _stateNewComment.value = null
    }

    fun resetProductToUpdate() {
        _productToUpdate.value = null
    }

    // Add this new method after your existing methods
    fun fetchCompradores() {
        viewModelScope.launch {
            try {
                val listaCompradores = getCompradoresUseCase.execute()
                // Ordenar compradores por puntaje de mayor a menor
                _compradores.value = listaCompradores.sortedByDescending { it.puntaje }
            } catch (e: Exception) {
                Log.e("CompradoresViewModel", "Error al obtener compradores", e)
                _compradores.value = emptyList()
            }
        }
    }


}