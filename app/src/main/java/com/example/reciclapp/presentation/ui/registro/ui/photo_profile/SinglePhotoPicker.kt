package com.example.reciclapp.presentation.ui.registro.ui.photo_profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Usuario

@Composable
fun SinglePhotoPicker(
    onImageUriReady: (Uri?) -> Unit,
    sizeImageProfile: Int,
    imageDefault: Uri = Uri.parse("android.resource://com.example.reciclapp/drawable/perfil"),
    user: Usuario = Usuario()
) {
    var imageUri by remember {
        mutableStateOf(
            if (user.urlImagenPerfil.isNotEmpty()) Uri.parse(user.urlImagenPerfil)
            else imageDefault
        )
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { result ->
            imageUri = result ?: imageDefault
            onImageUriReady(imageUri)
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        AsyncImage(
            model = imageUri,
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
                .offset(x = 45.dp, y = (40).dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "Pick Image",
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
