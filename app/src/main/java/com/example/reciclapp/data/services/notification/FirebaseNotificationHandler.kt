package com.example.reciclapp.data.services.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.reciclapp.MainActivity
import com.example.reciclapp.R
import com.example.reciclapp.di.MessagingServiceEntryPoint
import com.example.reciclapp.domain.usecases.mensajes.GetMensajeUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CHANNEL_ID = "high_importance_channel"
private const val TAG = "FirebaseMessagingHandler"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagingHandler: FirebaseMessagingService() {

    @Inject
    lateinit var getMensajeUseCase: GetMensajeUseCase

    override fun onCreate() {
        super.onCreate()
        // Initialize getMensajeUseCase using the EntryPoint
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            MessagingServiceEntryPoint::class.java
        )
        getMensajeUseCase = entryPoint.getMensajeUseCase()
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

        val notification = remoteMessage.notification
        val data = remoteMessage.data

        val idMensaje = data["clave1"] ?: ""
        val idComprador = data["clave2"] ?: ""


        if (notification != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mensaje = getMensajeUseCase(idMensaje)

                    if (mensaje == null) return@launch

                    Log.d(TAG, "Mensaje recibido: $mensaje")

                    NotificationEventBus.emitNotification(mensaje)

                    if (!isAppInForeground()) {
                        showNotification(notification.title, notification.body)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing message: ${e.message}")
                }
            }
        }
    }

    private fun isAppInForeground(): Boolean {
        //Cuando la aplicacion esta en segundo plano
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
    }

    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_leaf)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@FirebaseMessagingHandler,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }
    }
}