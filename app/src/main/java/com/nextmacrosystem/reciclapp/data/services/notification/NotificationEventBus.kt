package com.nextmacrosystem.reciclapp.data.services.notification

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NotificationEventBus {
    private val _notificationEvents = MutableSharedFlow<Mensaje>()
    val notificationEvents = _notificationEvents.asSharedFlow()

    suspend fun emitNotification(mensaje: Mensaje) {
        _notificationEvents.emit(mensaje)
    }
}