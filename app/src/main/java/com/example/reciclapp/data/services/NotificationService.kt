package com.example.reciclapp.data.services

import android.util.Log
import com.example.reciclapp.domain.entities.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class NotificationService @Inject constructor(
    private val client: OkHttpClient
) {
    private val baseUrl = "https://nextmacrosystem.net/api" // Tu dominio

    suspend fun sendNotification(
        token: String,
        title: String,
        body: String,
        additionalData: Map<String, String>? = null
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val json = JSONObject().apply {
                put("token", token)
                put("title", title)
                put("body", body)
                if (additionalData != null) {
                    put("additionalData", JSONObject(additionalData))
                }
            }

            val request = Request.Builder()
                .url("$baseUrl/send_notification.php")
                .post(json.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Error inesperado: ${response.code}")
                }
                return@withContext Result.Success(response.body?.string() ?: "")
            }
        } catch (e: Exception) {
            Log.e("NotificationApiService", "Error sending notification", e)
            return@withContext Result.Failure(e)
        }
    }
}