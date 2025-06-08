<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/data/services/notification/NotificationEventBus.kt
package com.example.reciclapp_bolivia.data.services.notification

import com.example.reciclapp_bolivia.domain.entities.Mensaje
========
package com.nextmacrosystem.reciclapp.data.services.notification

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/data/services/notification/NotificationEventBus.kt
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NotificationEventBus {
    private val _notificationEvents = MutableSharedFlow<Mensaje>()
    val notificationEvents = _notificationEvents.asSharedFlow()

    suspend fun emitNotification(mensaje: Mensaje) {
        _notificationEvents.emit(mensaje)
    }
}