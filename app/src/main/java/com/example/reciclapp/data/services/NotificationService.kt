package com.example.reciclapp.data.services

import android.util.Log
import com.example.reciclapp.core.ConfigFCM
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayInputStream

class NotificationService() {
    private val fcmUrl =
        "https://fcm.googleapis.com/v1/projects/${ConfigFCM.projectId}/messages:send"
    private val client = OkHttpClient()

    private suspend fun getAccessToken(): String {
        return withContext(Dispatchers.IO) {
            try {
                // Crear el JSON de credenciales como string
                val credentialsJson = """
                {
                    "type": "service_account",
                    "project_id": "${ConfigFCM.projectId}",
                    "private_key_id": "${ConfigFCM.privateKeyId}",
                    "private_key": "${ConfigFCM.privateKey}",
                    "client_email": "${ConfigFCM.clientEmail}",
                    "client_id": "${ConfigFCM.clientId}",
                    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                    "token_uri": "https://oauth2.googleapis.com/token",
                    "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/${ConfigFCM.clientEmail}"
                }
                """.trimIndent()

                // Convertir el JSON string a InputStream
                val credentials = GoogleCredentials
                    .fromStream(ByteArrayInputStream(credentialsJson.toByteArray()))
                    .createScoped("https://www.googleapis.com/auth/firebase.messaging")

                // Forzar la actualización del token
                credentials.refresh()
                val token = credentials.accessToken.tokenValue
                Log.d("NotificationService", "Access token: $token")
                token

            } catch (e: Exception) {
                Log.e("NotificationService", "Error getting access token: ${e.message}")
                throw e
            }
        }
    }

    suspend fun sendNotificationToUsers(
        tokenOfRecept: String,
        title: String,
        body: String,
        data: Map<String, String>? = null
    ) {
        if (tokenOfRecept.isEmpty()) {
            Log.d("NotificationService", "No token provided for notification")
            return
        }

        try {
            val token = getAccessToken()
            sendNotification(tokenOfRecept, title, body, data, token)
        } catch (e: Exception) {
            when {
                e.message?.contains("UNREGISTERED") == true -> {
                    Log.e("NotificationService", "Token no válido, necesita actualización")
                    // Aquí podrías implementar la lógica para solicitar un nuevo token
                    //generateNewToken()
                }
                else -> {
                    Log.e("NotificationService", "Error sending notification", e)
                    throw e
                }
            }
        }
    }

    private suspend fun sendNotification(
        userToken: String,
        title: String,
        body: String,
        data: Map<String, String>?,
        accessToken: String
    ) = withContext(Dispatchers.IO) {
        try {
            val message = JSONObject().apply {
                put("message", JSONObject().apply {
                    put("token", userToken)
                    put("notification", JSONObject().apply {
                        put("title", title)
                        put("body", body)
                    })
                    if (data != null) {
                        put("data", JSONObject(data))
                    }
                    // Agregar configuración de prioridad alta
                    put("android", JSONObject().apply {
                        put("priority", "high")
                    })
                })
            }

            val requestBody = message.toString()
                .toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(fcmUrl)
                .addHeader("Authorization", "Bearer $accessToken")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            Log.d("NotificationService", "Sending FCM request: ${message}")

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                if (!response.isSuccessful) {
                    Log.e("NotificationService", "Error sending notification: $responseBody")
                    throw Exception("Failed to send notification: ${response.code} - $responseBody")
                }
                Log.d("NotificationService", "Notification sent successfully: $responseBody")
            }
        } catch (e: Exception) {
            Log.e("NotificationService", "Error in sendNotification", e)
            throw e
        }
    }
}