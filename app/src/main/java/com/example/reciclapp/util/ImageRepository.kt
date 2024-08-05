package com.example.reciclapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import coil.transform.CircleCropTransformation
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val imageLoader = ImageLoader.Builder(context).build()

    suspend fun loadImage(url: String): Bitmap? {
        return if (url.isNotEmpty()) {
            val request = ImageRequest.Builder(context).data(url).size(60, 60).scale(Scale.FILL)
                .transformations(CircleCropTransformation()).build()

            val result = withContext(Dispatchers.IO) {
                imageLoader.execute(request)
            }

            if (result is SuccessResult) {
                (result.drawable as? BitmapDrawable)?.bitmap
            } else {
                null
            }
        } else {
            null
        }
    }
}
