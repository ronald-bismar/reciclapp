package com.nextmacrosystem.reciclapp.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UsuarioRepository
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val saveUserPreferencesUseCase: SaveUserPreferencesUseCase,
    private val firebaseAuth: FirebaseAuth
) : UsuarioRepository {

    override suspend fun getUsuario(idUsuario: String): Usuario? {
        val snapshot = service.collection("usuario")
            .document(idUsuario)
            .get()
            .await()
        return snapshot.toObject(Usuario::class.java)
    }

    override suspend fun registrarUsuario(user: Usuario) {
        val result = firebaseAuth.createUserWithEmailAndPassword(user.correo, user.contrasena)
            .await()

        if (result.user != null) {
            service.collection("usuario")
                .document(user.idUsuario)
                .set(user)
                .await()
        }
    }

    override suspend fun actualizarUsuario(user: Usuario) {
        service.collection("usuario")
            .document(user.idUsuario)
            .set(user)
            .await()

        saveUserPreferencesUseCase.execute(user)
    }

    override suspend fun eliminarUsuario(idUsuario: String) {
        service.collection("usuario")
            .document(idUsuario.toString())
            .delete()
            .await()
    }

    override suspend fun getAllUsers(): MutableList<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val querySnapshot = service.collection("usuario")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { usuarios.add(it) }
        }
        return usuarios
    }

    override suspend fun cambiarTipoDeUsuario(usuario: Usuario) {
        service.collection("usuario")
            .document(usuario.idUsuario.toString())
            .set(usuario)
            .await()
    }

    suspend fun getUsuarioByEmail(email: String): Usuario? {
        val usuarios = mutableListOf<Usuario>()
        val querySnapshot = service.collection("usuario").whereEqualTo("correo", email)
            .get().await()

        for (document in querySnapshot.documents) {
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { usuarios.add(it) }
        }
        return usuarios.firstOrNull()
    }

    override suspend fun loginUsuario(email: String, password: String): Usuario? {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                withContext(Dispatchers.IO) {
                    getUsuarioByEmail(it.email ?: "")
                }
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepositoryImpl", "Error al iniciar sesión: ${e.message}")
            return null
        }
    }

    override suspend fun actualizarImagenPerfil(idUsuario: String, nuevaUrlImagen: String) {
        service.collection("usuario")
            .document(idUsuario)
            .update("urlImagenPerfil", nuevaUrlImagen)
            .await()
    }
}
