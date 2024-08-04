package com.example.reciclapp.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.util.UUID

class StorageUtil {

    companion object {

        /**
         * Sube una imagen a Firebase Storage y devuelve la URL de descarga a través de la función de callback.
         *
         * @param uri La URI de la imagen a subir.
         * @param context El contexto donde se mostrará el mensaje del toast.
         * @param onComplete Una funcion callback que se ejecuta cuando se haya realizado la operacion.
         */
        fun uploadToStorage(uri: Uri, context: Context, onComplete: (String?) -> Unit) {
            val storage = Firebase.storage
            val storageRef = storage.reference
            val uniqueImageName = UUID.randomUUID().toString()
            val imageRef: StorageReference = storageRef.child("images_profiles/$uniqueImageName.jpg")

            try {
                val byteArray = context.contentResolver.openInputStream(uri)?.use(InputStream::readBytes)
                if (byteArray != null) {
                    val uploadTask = imageRef.putBytes(byteArray)
                    uploadTask.addOnFailureListener {
                        showToast(context, "Upload failed")
                        onComplete(null)
                    }.addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                            onComplete(downloadUrl.toString())
                        }.addOnFailureListener {
                            onComplete(null)
                        }
                    }
                } else {
                    showToast(context, "Failed to read image")
                    onComplete(null)
                }
            } catch (e: Exception) {
                Log.d("StorageUtil","Error: ${e.message}")
                onComplete(null)
            }
        }

        private fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
