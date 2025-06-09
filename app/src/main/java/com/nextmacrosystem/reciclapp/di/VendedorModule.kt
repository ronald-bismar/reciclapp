package com.nextmacrosystem.reciclapp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.data.repositories.VendedorRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
import com.nextmacrosystem.reciclapp.domain.usecases.comentario.CrearComentarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.ComentarACompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.CompararPrecioEntreCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.EnviarMensajeACompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.GetVendedorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.LlamarACompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.PublicarProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.VerListaDeCompradoresCercanosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.VerMapaConCompradoresCercanosUseCase
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