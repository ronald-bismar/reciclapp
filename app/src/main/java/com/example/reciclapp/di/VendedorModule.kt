package com.example.reciclapp.di

import com.example.reciclapp.data.repositories.VendedorRepositoryImpl
import com.example.reciclapp.domain.repositories.VendedorRepository
import com.example.reciclapp.domain.usecases.comentario.CrearComentarioUseCase
import com.example.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.example.reciclapp.domain.usecases.vendedor.*
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VendedorModule {

    @Provides
    @Singleton
    fun provideVendedorRepository(
        service: FirebaseFirestore,
        registrarProductoUseCase: RegistrarProductoUseCase,
        crearComentarioUseCase: CrearComentarioUseCase
    ): VendedorRepository {
        return VendedorRepositoryImpl(service, registrarProductoUseCase, crearComentarioUseCase)
    }

    @Provides
    fun provideGetVendedorUseCase(repository: VendedorRepository): GetVendedorUseCase {
        return GetVendedorUseCase(repository)
    }

    @Provides
    fun provideGetVendedoresUseCase(repository: VendedorRepository): GetVendedoresUseCase {
        return GetVendedoresUseCase(repository)
    }

    @Provides
    fun providePublicarProductoUseCase(repository: VendedorRepository): PublicarProductoUseCase {
        return PublicarProductoUseCase(repository)
    }

    @Provides
    fun provideVerMapaConCompradoresCercanosUseCase(repository: VendedorRepository): VerMapaConCompradoresCercanosUseCase {
        return VerMapaConCompradoresCercanosUseCase(repository)
    }

    @Provides
    fun provideLlamarACompradorUseCase(repository: VendedorRepository): LlamarACompradorUseCase {
        return LlamarACompradorUseCase(repository)
    }

    @Provides
    fun provideEnviarMensajeACompradorUseCase(repository: VendedorRepository): EnviarMensajeACompradorUseCase {
        return EnviarMensajeACompradorUseCase(repository)
    }

    @Provides
    fun provideCompararPrecioEntreCompradoresUseCase(repository: VendedorRepository): CompararPrecioEntreCompradoresUseCase {
        return CompararPrecioEntreCompradoresUseCase(repository)
    }

    @Provides
    fun provideComentarACompradorUseCase(repository: VendedorRepository): ComentarACompradorUseCase {
        return ComentarACompradorUseCase(repository)
    }

    @Provides
    fun provideVerListaDeCompradoresCercanosUseCase(repository: VendedorRepository): VerListaDeCompradoresCercanosUseCase {
        return VerListaDeCompradoresCercanosUseCase(repository)
    }
}