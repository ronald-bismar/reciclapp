package com.nextmacrosystem.reciclapp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.data.repositories.CompradorRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.ActualizarDatosCompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.EliminarCompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.GetCompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.HacerOfertaPorMaterialesEnVentaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.PublicarListaDeMaterialesQueCompraUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.VerListaDePublicacionesDeProductosEnVentaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompradorModule {
    @Singleton
    @Provides
    fun provideCompradorRepository(service: FirebaseFirestore, registrarProductoUseCase: RegistrarProductoUseCase, listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase): CompradorRepository {
        return CompradorRepositoryImpl(service, registrarProductoUseCase, listarTodosLosProductosUseCase)
    }

    @Provides
    fun provideGetCompradorUseCase(repository: CompradorRepository): GetCompradorUseCase {
        return GetCompradorUseCase(repository)
    }

    @Provides
    fun provideActualizarDatosCompradorUseCase(repository: CompradorRepository): ActualizarDatosCompradorUseCase {
        return ActualizarDatosCompradorUseCase(repository)
    }

    @Provides
    fun provideEliminarCompradorUseCase(repository: CompradorRepository): EliminarCompradorUseCase {
        return EliminarCompradorUseCase(repository)
    }

    @Provides
    fun provideGetCompradoresUseCase(repository: CompradorRepository): GetCompradoresUseCase {
        return GetCompradoresUseCase(repository)
    }

    @Provides
    fun providePublicarListaDeMaterialesQueCompraUseCase(repository: CompradorRepository): PublicarListaDeMaterialesQueCompraUseCase {
        return PublicarListaDeMaterialesQueCompraUseCase(repository)
    }

    @Provides
    fun provideVerListaDePublicacionesDeProductosEnVentaUseCase(repository: CompradorRepository): VerListaDePublicacionesDeProductosEnVentaUseCase {
        return VerListaDePublicacionesDeProductosEnVentaUseCase(repository)
    }

    @Provides
    fun provideHacerOfertaPorMaterialesEnVentaUseCase(repository: CompradorRepository): HacerOfertaPorMaterialesEnVentaUseCase {
        return HacerOfertaPorMaterialesEnVentaUseCase(repository)
    }
}