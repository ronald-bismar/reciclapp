package com.example.reciclapp_bolivia.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.reciclapp_bolivia.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Clase repositorio para cargar y procesar imágenes.
 *
 * @param context el contexto de la aplicación.
 */
class ImageRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // Inicializa el ImageLoader de la librería Coil
    private val imageLoader = ImageLoader.Builder(context).build()

    companion object {
        // Tamaños fijos en dp que se convertirán a píxeles según la densidad del dispositivo
        private const val PROFILE_SIZE_DP = 39
        private const val MARKER_WIDTH_DP = 50
        private const val MARKER_HEIGHT_DP = 60
    }

    /**
     * Función de extensión para convertir dp a píxeles.
     *
     * @param dp el valor en píxeles independientes de densidad (dp).
     * @return el valor en píxeles.
     */
    private fun Context.dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    /**
     * Carga una imagen desde una URL, la procesa a un bitmap recortado circularmente,
     * y la combina con una imagen de marcador.
     *
     * @param url la URL de la imagen a cargar.
     * @return un bitmap combinado con la imagen de perfil centrada en una imagen de marcador, o null si la carga falla.
     */
    suspend fun loadImage(url: String): Bitmap? {
        // Retorna null si la URL está vacía
        if (url.isEmpty()) return null

        // Convertir tamaños dp a píxeles
        val profileSizePx = context.dpToPx(PROFILE_SIZE_DP).toInt()
        val markerWidthPx = context.dpToPx(MARKER_WIDTH_DP).toInt()
        val markerHeightPx = context.dpToPx(MARKER_HEIGHT_DP).toInt()


        // Construye la solicitud de imagen utilizando Coil
        val request = ImageRequest.Builder(context)
            .data(url)
            .size(profileSizePx, profileSizePx)  // Redimensiona la imagen a 67x67 píxeles
            .scale(Scale.FILL)
            .transformations(CircleCropTransformation())  // Aplica una transformación de recorte circular
            .build()

        // Ejecuta la solicitud de imagen en un hilo de fondo
        val result = withContext(Dispatchers.IO) {
            imageLoader.execute(request)
        }

        // Verifica si el resultado es exitoso y contiene un bitmap
        return if (result is SuccessResult) {
            val profileBitmap = (result.drawable as? BitmapDrawable)?.bitmap
            val markerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.marker)

            // Si el bitmap del perfil no es nulo, crea el bitmap combinado
            profileBitmap?.let { profile ->
                // Escalar el marcador al tamaño deseado
                val scaledMarker = Bitmap.createScaledBitmap(
                    markerBitmap,
                    markerWidthPx,
                    markerHeightPx,
                    true
                )
                createCombinedBitmap(profile, scaledMarker, markerWidthPx, markerHeightPx)
            }
        } else {
            null
        }
    }

    /**
     * Crea un bitmap combinado centrando una imagen de perfil sobre una imagen de marcador.
     *
     * @param profileBitmap el bitmap de la imagen de perfil.
     * @param markerBitmap el bitmap de la imagen de marcador.
     * @return el bitmap combinado.
     */
    private fun createCombinedBitmap(
        profileBitmap: Bitmap, markerBitmap: Bitmap, markerWidthPx: Int,
        markerHeightPx: Int
    ): Bitmap {
        // Crear bitmap final con tamaño fijo
        val combinedBitmap = Bitmap.createBitmap(
            markerWidthPx,
            markerHeightPx,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(combinedBitmap)

        // Calcular posición centrada para la foto de perfil
        val profileLeft = (markerWidthPx - profileBitmap.width) / 2f
        val profileTop = (markerHeightPx - profileBitmap.height) / 4f

        // Dibujar primero el marcador
        canvas.drawBitmap(markerBitmap, 0f, 0f, null)
        // Dibujar la foto de perfil encima
        canvas.drawBitmap(profileBitmap, profileLeft, profileTop, null)

        return combinedBitmap
    }
}
