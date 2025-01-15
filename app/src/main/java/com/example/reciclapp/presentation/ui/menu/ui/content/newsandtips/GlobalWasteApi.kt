package com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Headers



data class NewsTipsResult(
    val results: List<NewsTipResponse>
)

data class ImageRequest(
    val image: String
)

data class PredictionResponse(
    val prediction: String,
    val confidence: Double
)

// Retrofit interface for API endpoints
interface GlobalWasteApi {
    @GET("news-tips")
    suspend fun getNewsTips(): NewsTipsResult

    @POST("predict")
    suspend fun predict(@Body imageRequest: ImageRequest): PredictionResponse

    companion object {
        private const val BASE_URL = "https://reciclapi-garbage-detection.p.rapidapi.com/"

        fun create(): GlobalWasteApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("x-rapidapi-key", "a27c306b3fmsh57a2e70cface907p149c6cjsn9d2700248277")
                        .header("x-rapidapi-host", "reciclapi-garbage-detection.p.rapidapi.com")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GlobalWasteApi::class.java)
        }
    }
}
