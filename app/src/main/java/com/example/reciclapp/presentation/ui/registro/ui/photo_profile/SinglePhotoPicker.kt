package com.example.reciclapp.presentation.ui.registro.ui.photo_profile
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Usuario

@OptIn(ExperimentalMaterial3Api::class)
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

    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    // Estado para manejar la visibilidad del ModalBottomSheet
    var showModalSheet by remember { mutableStateOf(false) }

    // Launcher para seleccionar imagen desde la galería
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { result ->
            imageUri = result ?: imageDefault
            bitmapImage = null
            onImageUriReady(imageUri)
        }
    )

    // Launcher para tomar una foto con la cámara
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { result ->
            if (result != null) {
                bitmapImage = result
                imageUri = null
            } else {
                bitmapImage = null
                imageUri = imageDefault
            }
            onImageUriReady(null) // Si quieres trabajar con el bitmap, no pasas Uri aquí
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        if (bitmapImage != null) {
            Image(
                bitmap = bitmapImage!!.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(sizeImageProfile.dp)
                    .clip(CircleShape)
            )
        } else {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(sizeImageProfile.dp)
                    .clip(CircleShape)
            )
        }

        IconButton(
            onClick = { showModalSheet = true },
            modifier = Modifier
                .offset(x = 45.dp, y = (45).dp)
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

    // Mostrar el ModalBottomSheet
    if (showModalSheet) {
        ModalBottomSheet(onDismissRequest = { showModalSheet = false }) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Seleccione una opción",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextButton(
                    onClick = {
                        showModalSheet = false
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Subir desde Galería", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        showModalSheet = false
                        takePhotoLauncher.launch()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tomar una fotografia", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
