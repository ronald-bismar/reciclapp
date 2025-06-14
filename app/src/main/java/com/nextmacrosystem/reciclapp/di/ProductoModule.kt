package com.nextmacrosystem.reciclapp.di

import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.data.repositories.ProductoRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import com.nextmacrosystem.reciclapp.domain.usecases.mensajes.SendMessageUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ActualizarProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.CompradorAceptaOfertaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.EliminarProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.GetProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarProductosPorCompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarProductosPorUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.MarcarProductoComoVendidoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ObtenerProductoVendedorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ObtenerProductosPorIdsUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.SumarPuntosDeProductosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.UpdateLikedProductoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.VendedorAceptaOfertaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductoModule {

    @Provides
    @Singleton
    fun provideProductoRepository(service: FirebaseFirestore, sendMessageUseCase: SendMessageUseCase): ProductoRepository {
        return ProductoRepositoryImpl(service, sendMessageUseCase)
    }

    @Provides
    fun provideGetProductoUseCase(repository: ProductoRepository): GetProductoUseCase {
        return GetProductoUseCase(repository)
    }

    @Provides
    fun provideRegistrarProductoUseCase(repository: ProductoRepository): RegistrarProductoUseCase {
        return RegistrarProductoUseCase(repository)
    }

    @Provides
    fun provideActualizarProductoUseCase(repository: ProductoRepository): ActualizarProductoUseCase {
        return ActualizarProductoUseCase(repository)
    }

    @Provides
    fun provideEliminarProductoUseCase(repository: ProductoRepository): EliminarProductoUseCase {
        return EliminarProductoUseCase(repository)
    }

    @Provides
    fun provideListarTodosLosProductosUseCase(productoRepository: ProductoRepository): ListarTodosLosProductosUseCase {
        return ListarTodosLosProductosUseCase(productoRepository)
    }

    @Provides
    fun provideListarProductosPorVendedorUseCase(repository: ProductoRepository): ListarProductosDeVendedorUseCase {
        return ListarProductosDeVendedorUseCase(repository)
    }

    @Provides
    fun provideUpdateLikedProductoUseCase(repository: ProductoRepository): UpdateLikedProductoUseCase {
        return UpdateLikedProductoUseCase(repository)
    }

    @Provides
    fun provideListarMaterialesUseCase(repository: ProductoRepository): ListarProductosPorCompradorUseCase {
        return ListarProductosPorCompradorUseCase(repository)
    }

    @Provides
    fun provideListarProductosPorUsuarioUseCase(repository: ProductoRepository): ListarProductosPorUsuarioUseCase {
        return ListarProductosPorUsuarioUseCase(repository)
    }

    @Provides
    fun provideMarcarProductoComoVendidoUseCase(repository: ProductoRepository): MarcarProductoComoVendidoUseCase {
        return MarcarProductoComoVendidoUseCase(repository)
    }

    @Provides
    fun provideObtenerProductosConVendedoresUseCase(repository: ProductoRepository): ObtenerProductoVendedorUseCase {
        return ObtenerProductoVendedorUseCase(repository)
    }

    @Provides
    fun provideSumarPuntosDeProductosUseCase(repository: ProductoRepository): SumarPuntosDeProductosUseCase {
        return SumarPuntosDeProductosUseCase(repository)
    }

    @Provides
    fun provideVendedorAceptaOfertaUseCase(productoRepository: ProductoRepository): VendedorAceptaOfertaUseCase {
        return VendedorAceptaOfertaUseCase(productoRepository)
    }

    @Provides
    fun provideCompradorAceptaOfertaUseCase(productoRepository: ProductoRepository): CompradorAceptaOfertaUseCase {
        return CompradorAceptaOfertaUseCase(productoRepository)
    }

    @Provides
    fun provideObtenerProductosPorIdsUseCase(productoRepository: ProductoRepository): ObtenerProductosPorIdsUseCase {
        return ObtenerProductosPorIdsUseCase(productoRepository)
    }

}