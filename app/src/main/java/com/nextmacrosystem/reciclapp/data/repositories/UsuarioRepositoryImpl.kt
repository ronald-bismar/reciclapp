package com.nextmacrosystem.reciclapp.data.repositories

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UsuarioRepository
import com.example.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val saveUserPreferencesUseCase: SaveUserPreferencesUseCase
) : UsuarioRepository {

    override suspend fun getUsuario(idUsuario: String): Usuario? {
        val snapshot = service.collection("usuario")
            .document(idUsuario)
            .get()
            .await()
        return snapshot.toObject(Usuario::class.java)
    }

    override suspend fun registrarUsuario(user: Usuario) {
        service.collection("usuario")
            .document(user.idUsuario)
            .set(user)
            .await()
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

    override suspend fun loginUsuario(email: String, password: String): Usuario? {
        val usuarios = mutableListOf<Usuario>()
        val querySnapshot = service.collection("usuario")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { usuarios.add(it) }
        }
        return usuarios.find { it.correo == email && it.contrasena == password }
    }
}
