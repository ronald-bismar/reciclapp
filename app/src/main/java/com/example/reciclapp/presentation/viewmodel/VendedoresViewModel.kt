package com.example.reciclapp.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.producto.ActualizarProductoUseCase
import com.example.reciclapp.domain.usecases.producto.CalcularCO2AhorradoEnKilos
import com.example.reciclapp.domain.usecases.producto.EliminarProductoUseCase
import com.example.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.example.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.example.reciclapp.domain.usecases.producto.ObtenerProductoVendedorUseCase
import com.example.reciclapp.domain.usecases.producto.ObtenerProductosPredeterminados
import com.example.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.example.reciclapp.domain.usecases.producto.UpdateLikedProductoUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedorUseCase
import com.example.reciclapp.util.StorageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "VendedoresViewModel"

@HiltViewModel
class VendedoresViewModel @Inject constructor(
    private val getVendedorUseCase: GetVendedorUseCase,
    private val listarProductosDeVendedorUseCase: ListarProductosDeVendedorUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase,
    private val updateLikedProductoUseCase: UpdateLikedProductoUseCase,
    private val registrarProductoUseCase: RegistrarProductoUseCase,
    private val calcularCO2AhorradoEnKilos: CalcularCO2AhorradoEnKilos,
    private val obtenerProductosPredeterminados: ObtenerProductosPredeterminados,
    private val eliminarProductoUseCase: EliminarProductoUseCase,
    private val actualizarProductoUseCase: ActualizarProductoUseCase,
    private val getProductoVendedorUseCase: ObtenerProductoVendedorUseCase
) : ViewModel() {
    private val _showToast = MutableSharedFlow<String>()
    val showToast: SharedFlow<String> = _showToast

    init {
        loadUserPreferences()
    }

    private val _user = MutableLiveData<Usuario?>()
    val user: LiveData<Usuario?> get() = _user

    private val _selectedVendedor = MutableStateFlow<Usuario?>(null)
    val selectedVendedor: StateFlow<Usuario?> = _selectedVendedor

    private val _productos = MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productos: StateFlow<MutableList<ProductoReciclable>> = _productos

    private val _productosActivos = MutableStateFlow(0)
    val productosActivos: StateFlow<Int> = _productosActivos

    private val _cantidadMeGustasEnProductos = MutableStateFlow(0)
    val cantidadMeGustasEnProductos: StateFlow<Int> = _cantidadMeGustasEnProductos

    private val _productosPredeterminados = MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productosPredeterminados: StateFlow<MutableList<ProductoReciclable>> = _productosPredeterminados

    private val _productosConVendedores = MutableStateFlow<List<Pair<ProductoReciclable, Usuario>>>(emptyList())
    val productosConVendedores: StateFlow<List<Pair<ProductoReciclable, Usuario>>> = _productosConVendedores

    private val _co2AhorradoEnKilos = MutableStateFlow(0.0)
    val co2AhorradoEnKilos: StateFlow<Double> = _co2AhorradoEnKilos

    private val _productToUpdate = MutableStateFlow<ProductoReciclable?>(null)
    val productToUpdate: StateFlow<ProductoReciclable?> = _productToUpdate

    private val _productsToSale = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productsToSale: StateFlow<List<ProductoReciclable>> = _productsToSale

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchVendedorById(idVendedor: String) {
        Log.d(TAG, "fetchVendedorById: Id vendedor $idVendedor")
        viewModelScope.launch {
            _selectedVendedor.value = getVendedorUseCase.execute(idVendedor)
        }
    }

    fun fetchProductosByVendedor(userId: String) {
        viewModelScope.launch {
            _productos.value = listarProductosDeVendedorUseCase.execute(userId)
            contarProductosActivos()
            calcularCO2AhorradoEnKilos()
            calcularMeGustasEnProductos()
        }
    }

    private fun calcularMeGustasEnProductos() {
        _cantidadMeGustasEnProductos.value = _productos.value.sumOf { it.meGusta }
    }

    private fun loadUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _user.postValue(usuario)
        }
    }

    private fun calcularCO2AhorradoEnKilos() {
        viewModelScope.launch {
            val productosReciclables = _productos.value
            _co2AhorradoEnKilos.value = calcularCO2AhorradoEnKilos.execute(productosReciclables)
        }
    }

    fun obtenerProductosPredeterminados() {
        viewModelScope.launch {
            _productosPredeterminados.value = obtenerProductosPredeterminados.execute()
        }
    }

    fun setProductsToSale(products: List<ProductoReciclable>) {
        _productsToSale.value = products
    }


    fun fetchAllProducts() {
        viewModelScope.launch {
            _productos.value = listarTodosLosProductosUseCase.execute().toMutableList()
        }
    }

    fun fetchAllProductsAndVendedor() {
        viewModelScope.launch {
            _productosConVendedores.value = getProductoVendedorUseCase.execute()
        }
    }

    fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean) {
        viewModelScope.launch { updateLikedProductoUseCase.execute(productoReciclable, isLiked) }
    }

    fun registrarNuevoProducto(
        productoReciclable: ProductoReciclable,
        uriImagenProducto: List<Uri>,
        context: Context
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Subir la primera imagen (si existe)
                val primeraImagenUri = uriImagenProducto.firstOrNull()
                val urlImagen = primeraImagenUri?.let { uri ->
                    StorageUtil.uploadToStorage(uri, context) // Esperar a que se complete la subida
                }

                // Crear una copia del producto con la nueva URL de la imagen
                val productoActualizado = productoReciclable.copy(
                    urlImagenProducto = urlImagen ?: ""
                )

                // Registrar el producto
                registrarProductoUseCase.execute(productoActualizado)

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
        productoReciclable: ProductoReciclable,
        uriImagenProducto: List<Uri>,
        context: Context
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Subir la primera imagen (si existe)
                val primeraImagenUri = uriImagenProducto.firstOrNull()
                val urlImagen = primeraImagenUri?.let { uri ->
                    StorageUtil.uploadToStorage(uri, context) // Esperar a que se complete la subida
                }

                // Actualizar el producto con la nueva URL de la imagen
                val productoActualizado = productoReciclable.copy(
                    urlImagenProducto = urlImagen ?: ""
                )

                // Ejecutar la actualización del producto
                actualizarProductoUseCase.execute(productoActualizado)
                fetchProductosByVendedor(productoReciclable.idVendedor)

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

    fun contarProductosActivos() {
        viewModelScope.launch {
            Log.d("VendedoresViewModel", "Productos ${_productos.value}")


            _productosActivos.value = _productos.value.filter { !it.fueVendida }.size

            Log.d("VendedoresViewModel", "Productos activos: ${_productosActivos.value}")
        }
    }

    fun setProductToUpdate(productoReciclable: ProductoReciclable) {
        _productToUpdate.value = productoReciclable
    }

    fun eliminarProducto(productoReciclable: ProductoReciclable) {
        viewModelScope.launch {
            eliminarProductoUseCase.execute(productoReciclable.idProducto)
            _showToast.emit("Producto eliminado correctamente")
            val listaActualizada = _productos.value.toMutableList()
            listaActualizada.remove(productoReciclable)
            _productos.value = listaActualizada
        }
    }

    fun resetProductToUpdate() {
        _productToUpdate.value = null
    }
}