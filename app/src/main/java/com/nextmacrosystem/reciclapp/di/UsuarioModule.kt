package com.nextmacrosystem.reciclapp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.data.repositories.UsuarioRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.UsuarioRepository
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.ActualizarImagenPerfilUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.ActualizarUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.CambiarTipoDeUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.EliminarUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.GetAllUsersUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.GetUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.RegistrarUsuarioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsuarioModule {
    @Provides
    @Singleton
    fun provideUsuarioRepository(service: FirebaseFirestore, saveUserPreferencesUseCase: SaveUserPreferencesUseCase): UsuarioRepository {
        return UsuarioRepositoryImpl(service, saveUserPreferencesUseCase)
    }
    @Provides
    fun provideGetUsuarioUseCase(usuarioRepository: UsuarioRepository): GetUsuarioUseCase {
        return GetUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideRegistrarUsuarioUseCase(usuarioRepository: UsuarioRepository): RegistrarUsuarioUseCase {
        return RegistrarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideActualizarUsuarioUseCase(usuarioRepository: UsuarioRepository): ActualizarUsuarioUseCase {
        return ActualizarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideEliminarUsuarioUseCase(usuarioRepository: UsuarioRepository): EliminarUsuarioUseCase {
        return EliminarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideGetAllUsersUseCase(usuarioRepository: UsuarioRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(usuarioRepository)
    }

    @Provides
    fun provideCambiarTipoDeUsuarioUseCase(usuarioRepository: UsuarioRepository): CambiarTipoDeUsuarioUseCase {
        return CambiarTipoDeUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideActualizarImagenPerfilUseCase(usuarioRepository: UsuarioRepository): ActualizarImagenPerfilUseCase {
        return ActualizarImagenPerfilUseCase(usuarioRepository)
    }
}