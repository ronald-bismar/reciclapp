package com.example.reciclapp.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reciclapp.MainActivity
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Mensaje
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val CHANNEL_ID = "high_importance_channel"
private const val TAG = "FirebaseMessagingHandler"

class FirebaseMessagingHandler : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones de Reciclaje",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificaciones de nuevas solicitudes de reciclaje"
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
            }

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Message received from: ${remoteMessage.from}")
        Log.d(TAG, "Data payload: ${remoteMessage.data}")

        val notification = remoteMessage.notification
        val data = remoteMessage.data

        // Extraer additionalData
        val additionalData = data["additionalData"]?.let {
            try {
                JSONObject(it).let { json ->
                    mapOf(
                        "idMensaje" to (json.optString("clave1") ?: ""),
                        "idComprador" to (json.optString("clave2") ?: "")
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing additionalData: ${e.message}")
                emptyMap()
            }
        } ?: emptyMap()

        // Combinar los datos
        val combinedData = data + additionalData

        if (notification != null) {
            val idMensaje = combinedData["idMensaje"] ?: ""

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Si tienes un repositorio para obtener mensajes
                    // val mensaje = mensajeRepository.getMensajeById(idMensaje)

                    // O crear uno temporal con los datos disponibles
                    val mensaje = Mensaje(
                        idMensaje = idMensaje,
                        contenido = notification.body ?: "",
                        idComprador = combinedData["idComprador"] ?: "",
                        idVendedor = "",  // No tenemos esta información en la notificación
                        idProductoConPrecio = ""  // No tenemos esta información en la notificación
                    )

                    launch(Dispatchers.Main) {
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing message: ${e.message}")
                }
            }
        }
    }
}