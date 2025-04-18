package com.example.reciclapp.di

import com.example.reciclapp.data.repositories.MensajeRepositoryImpl
import com.example.reciclapp.data.services.NotificationService
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.domain.usecases.mensaje.CompradorEnviaMensajeAVendedorUseCase
import com.example.reciclapp.domain.usecases.mensaje.ObtenerMensajePorUsuarioUseCase
import com.example.reciclapp.domain.usecases.mensaje.VendedorEnviaMensajeACompradorUseCase
import com.example.reciclapp.domain.usecases.mensajes.DeleteMensajeUseCase
import com.example.reciclapp.domain.usecases.mensajes.GetMensajeUseCase
import com.example.reciclapp.domain.usecases.mensajes.SaveMensajeUseCase
import com.example.reciclapp.domain.usecases.mensajes.UpdateMensajeUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MensajeModule {

    @Provides
    @Singleton
    fun provideMensajeRepository(service: FirebaseFirestore, notificationService: NotificationService): MensajeRepository {
        return MensajeRepositoryImpl(service, notificationService)
    }

    @Provides
    @Singleton
    fun provideGetMensajeByIdUseCase(repository: MensajeRepository): GetMensajeUseCase {
        return GetMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveMensajeUseCase(repository: MensajeRepository): SaveMensajeUseCase {
        return SaveMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateMensajeUseCase(repository: MensajeRepository): UpdateMensajeUseCase {
        return UpdateMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMensajeUseCase(repository: MensajeRepository): DeleteMensajeUseCase {
        return DeleteMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCompradorEnviaMensajeAVendedorUseCase(repository: MensajeRepository): CompradorEnviaMensajeAVendedorUseCase {
        return CompradorEnviaMensajeAVendedorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVendedorEnviaMensajeACompradorUseCase(repository: MensajeRepository): VendedorEnviaMensajeACompradorUseCase {
        return VendedorEnviaMensajeACompradorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObtenerMensajesPorUsuarioUseCase(repository: MensajeRepository): ObtenerMensajePorUsuarioUseCase {
        return ObtenerMensajePorUsuarioUseCase(repository)
    }
}