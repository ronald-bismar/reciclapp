package com.example.reciclapp_bolivia.presentation.ui.registro.ui.photo_profile
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.reciclapp_bolivia.R
import com.example.reciclapp_bolivia.domain.entities.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePhotoPicker(
    onImageUriReady: (Uri?) -> Unit,
    sizeImageProfile: Int,
    imageDefault: Uri = "android.resource://com.example.reciclapp/drawable/perfil".toUri(),
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

    val context = LocalContext.current

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

    // Launcher para solicitar permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Si se concede el permiso, lanzar la cámara
                takePhotoLauncher.launch()
            } else {
                // Si se rechaza el permiso, mostrar un mensaje
                Toast.makeText(
                    context,
                    "Se requiere permiso de cámara para tomar fotos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )

    // Launcher para seleccionar imagen desde la galería
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { result ->
            imageUri = result ?: imageDefault
            bitmapImage = null
            onImageUriReady(imageUri)
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
                        // Verificar permisos de cámara antes de lanzar
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                // Ya tenemos el permiso, lanzar la cámara directamente
                                takePhotoLauncher.launch()
                            }
                            else -> {
                                // Solicitar permiso
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
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
