package com.example.reciclapp.presentation.ui.registro.ui.photo_profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.viewmodel.UserViewModel

/**
 * Composable para seleccionar una imagen de perfil.
 *
 * @param onImageUriReady Función de callback que recibe la URI de la imagen seleccionada.
 * @param sizeImageProfile Tamaño de la imagen de perfil.
 * @param imageDefault URI de la imagen por defecto si no se selecciona ninguna.
 **/
@Composable
fun SinglePhotoPicker(
    onImageUriReady: (Uri?) -> Unit,
    sizeImageProfile: Int,
    imageDefault: Uri = Uri.parse("android.resource://com.example.reciclapp/drawable/perfil"),
    user: Usuario = Usuario()
) {
    var uri by remember {
        mutableStateOf<Uri?>(
            if (user.urlImagenPerfil.isNotEmpty()) Uri.parse(
                user.urlImagenPerfil
            ) else imageDefault
        )
    }
    Log.d("ResultImagePicker", "uri remember: ${uri}")
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { result ->
            uri = result
                ?: if (user.urlImagenPerfil.isNotEmpty()) Uri.parse(user.urlImagenPerfil) else imageDefault
            onImageUriReady(uri) // Pasar la URI de la imagen
            Log.d("ResultImagePicker", "result: ${result}")
            Log.d("ResultImagePicker", "uri: ${uri}")
            Log.d("ResultImagePicker", "user?.urlImagenPerfil: ${user.urlImagenPerfil}")
            Log.d("ResultImagePicker", "imageDefault: $imageDefault")
        }
    )

    val imageToShow = uri
        ?: if (user.urlImagenPerfil.isNotEmpty()) Uri.parse(user.urlImagenPerfil) else imageDefault
    Log.d("ResultImagePicker", "imageToShow: $imageToShow")

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageToShow,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(sizeImageProfile.dp)
                    .clip(CircleShape)
            )

            IconButton(
                onClick = {
                    singlePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .offset(x = 50.dp, y = (-50).dp) // Opcional: agregar padding superior al botón
                    .clip(CircleShape)
                    .background(Color.LightGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Pick Image",
                    modifier = Modifier.size(26.dp) // Tamaño del ícono
                )
            }
        }
    }
}
