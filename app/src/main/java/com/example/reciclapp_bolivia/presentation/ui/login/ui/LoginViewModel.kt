package com.example.reciclapp_bolivia.presentation.ui.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import com.example.reciclapp_bolivia.domain.usecases.usuario.LoginUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsuarioUseCase: LoginUsuarioUseCase,
    private val saveUserUseCase: SaveUserPreferencesUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<Result<Usuario>?>()
    val loginState: MutableLiveData<Result<Usuario>?> get() = _loginState

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()


    fun onLoginSelected() {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching { //try
                val user = loginUsuarioUseCase.execute(email.value!!, password.value!!)
                if (user != null) {
                    saveUserUseCase.execute(user)
                    _loginState.value = Result.success(user)
                } else {
                    _loginState.value = Result.failure(Exception("Usuario no encontrado"))
                }
            }.onFailure { e ->
                _loginState.value = Result.failure(e)
            }.also {
                _isLoading.value = false
            }
        }
    }

    fun loginWithChrome() {
        // Implement logic for Chrome login
    }

    fun onForgotPassword() {
        // Implement logic for password reset
    }

    fun resetState() {
        _loginState.value = null
        _email.value = ""
        _password.value = ""
        _loginEnable.value = false
        _isLoading.value = false
    }
}
