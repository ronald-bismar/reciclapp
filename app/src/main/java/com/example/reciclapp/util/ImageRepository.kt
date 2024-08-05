package com.example.reciclapp.util

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
import com.example.reciclapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val imageLoader = ImageLoader.Builder(context).build()

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    suspend fun loadImage(url: String): Bitmap? {
        return if (url.isNotEmpty()) {
            val request = ImageRequest.Builder(context)
                .data(url)
                .size(67, 67)
                .scale(Scale.FILL)
                .transformations(CircleCropTransformation())
                .build()

            val result = withContext(Dispatchers.IO) {
                imageLoader.execute(request)
            }

            if (result is SuccessResult) {
                val imagenPerfilBitMap = (result.drawable as? BitmapDrawable)?.bitmap
                val imagenMarkerAsBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.marker)

                if (imagenPerfilBitMap != null) {
                    // Convert dp to pixels
                    val markerSize = dpToPx(50, context)

                    // Calculate the scaled width for the marker image while maintaining aspect ratio
                    val aspectRatio = imagenMarkerAsBitmap.width.toFloat() / imagenMarkerAsBitmap.height
                    val scaledMarkerHeight = (markerSize / aspectRatio).toInt()

                    // Scale the marker image
                    val scaledMarkerBitmap = Bitmap.createScaledBitmap(
                        imagenMarkerAsBitmap,
                        markerSize,
                        scaledMarkerHeight,
                        true
                    )

                    // Create a new Bitmap with the dimensions of the scaled marker bitmap
                    val combinedBitmap = Bitmap.createBitmap(
                        markerSize,
                        scaledMarkerHeight,
                        scaledMarkerBitmap.config
                    )

                    val canvas = Canvas(combinedBitmap)
                    // Draw the profile image first
                    canvas.drawBitmap(imagenPerfilBitMap, 10f, 10f, null)
                    // Draw the marker image on top of the profile image
                    canvas.drawBitmap(scaledMarkerBitmap, 0f, 0f, null)

                    combinedBitmap
                } else {
                    null
                }
            } else {
                null
            }
        } else {
            null
        }
    }
}
