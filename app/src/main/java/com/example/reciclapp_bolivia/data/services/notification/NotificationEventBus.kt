package com.example.reciclapp_bolivia.data.services.notification

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NotificationEventBus {
    private val _notificationEvents = MutableSharedFlow<Mensaje>()
    val notificationEvents = _notificationEvents.asSharedFlow()

    suspend fun emitNotification(mensaje: Mensaje) {
        _notificationEvents.emit(mensaje)
    }
}