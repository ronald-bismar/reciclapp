package com.nextmacrosystem.reciclapp.data.services.notification

import android.util.Log
import com.nextmacrosystem.reciclapp.domain.entities.Result
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
    private val baseUrl = "https://nextmacrosystem.net/api"

    suspend fun sendNotification(bodyNotification: Map<String, Any>): Result<String> = withContext(Dispatchers.IO) {
        Log.d("NotificationApiService", "Sending notification with body: $bodyNotification")
        try {
            val jsonBody = JSONObject(bodyNotification).toString()

            val request = Request.Builder()
                .url("$baseUrl/send_notification.php")
                .header("Accept", "application/json") // Add proper headers
                .post(jsonBody.toRequestBody("application/json".toMediaType()))
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
    }}