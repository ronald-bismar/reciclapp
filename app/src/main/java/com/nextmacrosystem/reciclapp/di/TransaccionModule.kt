package com.nextmacrosystem.reciclapp.di

import com.example.reciclapp.data.repositories.TransaccionRepositoryImpl
import com.example.reciclapp.domain.repositories.TransaccionRepository
import com.example.reciclapp.domain.usecases.transaccion.ConfirmarTransaccionPendienteUseCase
import com.example.reciclapp.domain.usecases.transaccion.CrearTransaccionPendienteUseCase
import com.example.reciclapp.domain.usecases.transaccion.GetTransaccionesPendientesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransaccionModule {

    @Provides
    @Singleton
    fun provideTransaccionRepository(service: FirebaseFirestore): TransaccionRepository {
        return TransaccionRepositoryImpl(service)
    }

    @Provides
    fun provideGetTransaccionesPendientesUseCase(repository: TransaccionRepository): GetTransaccionesPendientesUseCase {
        return GetTransaccionesPendientesUseCase(repository)
    }

    @Provides
    fun provideCrearTransaccionPendienteUseCase(repository: TransaccionRepository): CrearTransaccionPendienteUseCase {
        return CrearTransaccionPendienteUseCase(repository)
    }

    @Provides
    fun provideConfirmarTransaccionPendienteUseCase(repository: TransaccionRepository): ConfirmarTransaccionPendienteUseCase {
        return ConfirmarTransaccionPendienteUseCase(repository)
    }
}