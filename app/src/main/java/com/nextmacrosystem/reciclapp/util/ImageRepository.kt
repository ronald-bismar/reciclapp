package com.nextmacrosystem.reciclapp.util

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
import com.nextmacrosystem.reciclapp.R
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

        // Construye la solicitud de imagen utilizando Coil
        val request = ImageRequest.Builder(context)
            .data(url)
            .size(80, 80)  // Redimensiona la imagen a 67x67 píxeles
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
                createCombinedBitmap(profile, markerBitmap)
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
    private fun createCombinedBitmap(profileBitmap: Bitmap, markerBitmap: Bitmap): Bitmap {
        // Convierte 50dp a píxeles para el tamaño del marcador
        val markerSize = context.dpToPx(50)
        // Calcula la relación de aspecto de la imagen del marcador
        val aspectRatio = markerBitmap.width.toFloat() / markerBitmap.height
        // Escala la altura de la imagen del marcador manteniendo la relación de aspecto
        val scaledMarkerHeight = (markerSize / aspectRatio).toInt()

        // Escala la imagen del marcador al tamaño calculado
        val scaledMarkerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerSize, scaledMarkerHeight, true)
        // Crea un nuevo bitmap con las dimensiones del bitmap del marcador escalado
        val combinedBitmap = Bitmap.createBitmap(markerSize, scaledMarkerHeight, scaledMarkerBitmap.config)

        // Inicializa un lienzo para dibujar el bitmap combinado
        val canvas = Canvas(combinedBitmap)
        // Calcula la posición para centrar el bitmap del perfil del usuario en el bitmap del marcador
        val profileLeft = (markerSize - profileBitmap.width) / 2f
        val profileTop = (scaledMarkerHeight - profileBitmap.height) / 4f
        val markerLeft = (markerSize - scaledMarkerBitmap.width) / 2f
        val markerTop = (scaledMarkerHeight - scaledMarkerBitmap.height) / 2f

        // Dibuja primero la imagen de perfil
        canvas.drawBitmap(profileBitmap, profileLeft, profileTop, null)
        // Dibuja la imagen del marcador encima de la imagen de perfil
        canvas.drawBitmap(scaledMarkerBitmap, markerLeft, markerTop, null)

        // Retorna el bitmap combinado
        return combinedBitmap
    }
}
