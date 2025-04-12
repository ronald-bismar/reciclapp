package com.example.reciclapp.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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

class FirebaseMessagingHandler : FirebaseMessagingService() {
    companion object {
        private const val CHANNEL_ID = "high_importance_channel"
        private const val TAG = "FirebaseMessagingHandler"
    }

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

        val notification = remoteMessage.notification
        val data = remoteMessage.data

        if (notification != null) {
            // Crear el mensaje con los datos recibidos
            val mensaje = Mensaje(
                idMensaje = data["idMensaje"] ?: "",
                contenido = notification.body ?: "",
                idProductoConPrecio = data["idProductoConPrecio"] ?: "",
                idComprador = data["idComprador"] ?: "",
                idVendedor = data["idVendedor"] ?: ""
            )

            // Mostrar la notificación
            showNotification(notification, data, mensaje)
        }
    }

    private fun showNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>,
        mensaje: Mensaje
    ) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("notification_data", JSONObject(data).toString())
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_leaf)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(notification.hashCode(), builder.build())
        }

        // Mostrar diálogo si la app está en primer plano
        showNotificationDialog(mensaje)
    }

    private fun showNotificationDialog(mensaje: Mensaje) {
        Log.d(TAG, "Showing notification dialog")
        Log.d(TAG, "Mensaje: $mensaje")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New token generated: $token")

        // Guardar el token localmente
        val sharedPreferences = getSharedPreferences("FCM", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("fcm_token", token).apply()

        // Enviar el token al servidor
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Aquí deberías implementar la lógica para actualizar el token en tu backend
                // Por ejemplo:
                // userViewModel.updateToken(token)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating token", e)
            }
        }
    }

    private fun sendTokenToServer(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Implementar la lógica para enviar el token a tu servidor
            } catch (e: Exception) {
                Log.e(TAG, "Error sending token to server", e)
            }
        }
    }
}