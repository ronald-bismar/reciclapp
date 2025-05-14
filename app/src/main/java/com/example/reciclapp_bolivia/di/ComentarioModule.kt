package com.example.reciclapp_bolivia.di

import com.example.reciclapp_bolivia.data.repositories.ComentarioRepositoryImpl
import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
import com.example.reciclapp_bolivia.domain.usecases.comentario.ActualizarComentarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.comentario.CrearComentarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.comentario.EliminarComentarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.comentario.GetComentarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.comentario.GetComentariosUseCase
import com.example.reciclapp_bolivia.domain.usecases.comentario.ListarComentariosDeCompradorUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComentarioModule {
    @Provides
    @Singleton
    fun provideComentarioRepository(service: FirebaseFirestore): ComentarioRepository {
        return ComentarioRepositoryImpl(service)
    }
    @Provides
    fun provideCrearComentarioUseCase(comentarioRepository: ComentarioRepository): CrearComentarioUseCase {
        return CrearComentarioUseCase(comentarioRepository)
    }
    @Provides
    fun provideGetComentarioUseCase(comentarioRepository: ComentarioRepository): GetComentarioUseCase {
        return GetComentarioUseCase(comentarioRepository)
    }
    @Provides
    fun provideActualizarComentarioUseCase(comentarioRepository: ComentarioRepository): ActualizarComentarioUseCase {
        return ActualizarComentarioUseCase(comentarioRepository)
    }
    @Provides
    fun provideEliminarComentarioUseCase(comentarioRepository: ComentarioRepository): EliminarComentarioUseCase {
        return EliminarComentarioUseCase(comentarioRepository)
    }
    @Provides
    fun provideGetComentariosUseCase(comentarioRepository: ComentarioRepository): GetComentariosUseCase {
        return GetComentariosUseCase(comentarioRepository)
    }

    @Provides
    fun provideListarComentariosDeCompradorUseCase(comentarioRepository: ComentarioRepository): ListarComentariosDeCompradorUseCase {
        return ListarComentariosDeCompradorUseCase(comentarioRepository)
    }
}