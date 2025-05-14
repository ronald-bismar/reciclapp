package com.example.reciclapp_bolivia.presentation.ui.menu.ui

import ListOfCategorias
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Filter
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.reciclapp.R
import com.example.reciclapp_bolivia.domain.entities.Categoria
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.presentation.states.ClassifierState
import com.example.reciclapp_bolivia.presentation.viewmodel.ClassifierViewModel
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecyclableClassifierApp(classifierViewModel: ClassifierViewModel) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var result by rememberSaveable { mutableStateOf<String>("") }
    var displayedText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    val primaryGreen = Color(0xFF2E7D32)
    val secondaryGreen = Color(0xFF81C784)
    val backgroundColor = Color(0xFFF5F5F5)

    val classifierState by classifierViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Función para crear un nuevo archivo temporal
    fun createTempImageFile(): File {
        return File.createTempFile(
            "photo_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}_",
            ".jpg",
            context.cacheDir
        ).apply {
            deleteOnExit()
        }
    }

    // Estado para el archivo temporal
    var tempImageFile by remember { mutableStateOf(createTempImageFile()) }

    // Uri para la foto que se guardará
    var tempImageUri by remember {
        mutableStateOf(
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempImageFile
            )
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tempImageUri
            val bitmap = BitmapFactory.decodeFile(tempImageFile.absolutePath)
            classifierViewModel.sendPrompt(bitmap)
            // Limpiar el texto mientras se procesa
            displayedText = ""
            result = ""
            // Crear nuevo archivo temporal para la próxima foto
            tempImageFile = createTempImageFile()
            tempImageUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempImageFile
            )
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(tempImageUri)
        }
    }

    // Agregar el launcher para seleccionar imágenes de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            // Convertir la Uri a Bitmap y procesar
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val bitmap = BitmapFactory.decodeStream(stream)
                classifierViewModel.sendPrompt(bitmap)
                // Limpiar el texto mientras se procesa
                displayedText = ""
                result = ""
            }
        }
    }

    LaunchedEffect(classifierState) {
        when (classifierState) {
            is ClassifierState.Success -> {
                result = (classifierState as ClassifierState.Success).outputText
                isTyping = true
                displayedText = ""
                var currentIndex = 0
                while (currentIndex < result.length) {
                    displayedText = result.substring(0, currentIndex + 1)
                    currentIndex++
                    delay(30) // Ajusta esta velocidad según prefieras
                }
                isTyping = false
            }

            is ClassifierState.Error -> {
                result = (classifierState as ClassifierState.Error).errorMessage
                displayedText = result
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header with animated eco icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.stars),
                    contentDescription = "Recycle Icon",
                    modifier = Modifier
                        .size(40.dp),
                    tint = Color(0xFFFFBF00)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Clasificador de reciclaje con IA",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryGreen,
                    textAlign = TextAlign.Center
                )
            }

            // Image preview area with gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                secondaryGreen.copy(alpha = 0.1f),
                                secondaryGreen.copy(alpha = 0.2f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .build(),
                        contentDescription = "Captured Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    this@Column.AnimatedVisibility(
                        visible = classifierState is ClassifierState.Loading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Camera Icon",
                            modifier = Modifier
                                .size(64.dp),
                            tint = Color.DarkGray
                        )
                        Text(
                            text = "Toma una foto al reciclaje\nque deseas clasificar",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Result card with typing animation
            AnimatedVisibility(
                visible = displayedText.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Análisis",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryGreen
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = displayedText,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                        if (isTyping) {
                            Text(
                                text = "▋",
                                fontSize = 16.sp,
                                color = primaryGreen,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Modificar la sección de botones al final
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                cameraLauncher.launch(tempImageUri)
                            }

                            else -> {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryGreen
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Camera",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (imageUri == null) "Tomar Fotografía" else "Nueva Fotografía",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                // Nuevo botón para seleccionar de la galería
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondaryGreen
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Filter, // Necesitarás agregar este ícono
                        contentDescription = "Gallery",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Seleccionar de Galería",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                MaterialGuideButton()
            }
        }
    }
}

@Composable
fun MaterialGuideButton() {
    var showGuide by remember { mutableStateOf(false) }

    Button(
        onClick = { showGuide = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Help,
            contentDescription = "Guía de materiales"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("¿No estás seguro? Ver guía rápida")
    }

    if (showGuide) {
        MaterialGuideDialog(
            onDismiss = { showGuide = false }
        )
    }
}

@Composable
fun MaterialGuideDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Guía rápida de materiales") },
        text = {
            LazyColumn {
                // Opción 1: Usando itemsIndexed
                items(ListOfCategorias.categorias.size) { index ->
                    MaterialGuideItem(ListOfCategorias.categorias[index])
                }

                /* Opción 2: Si importas androidx.compose.foundation.lazy.items
                items(ListOfCategorias.categorias) { categoria ->
                    MaterialGuideItem(categoria)
                }
                */
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Entendido")
            }
        }
    )
}

@Composable
fun MaterialGuideItem(categoria: Categoria) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = categoria.nombre,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = categoria.descripcionCategoria,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PhotoGuideOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Guías de encuadre (corregido)
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color.White.copy(alpha = 0.5f),
                style = Stroke(width = 2f)
            )
        }

        // Consejos de foto
        Text(
            text = "Centra el objeto y asegura buena iluminación",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        )
    }
}

@Composable
fun ClassificationResult(result: String) {
    val parts = result.split("\n")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            parts.forEach { part ->
                when {
                    part.startsWith("MATERIAL:") -> {
                        Text(
                            text = part.substringAfter("MATERIAL:").trim(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    part.startsWith("CATEGORÍA:") -> {
                        Text(
                            text = part.substringAfter("CATEGORÍA:").trim(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    part.startsWith("CONSEJO RÁPIDO:") -> {
                        Text(
                            text = part.substringAfter("CONSEJO RÁPIDO:").trim(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetails(producto: ProductoReciclable?) {
    producto?.let {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFE3F2FD),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Detalles del Material",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Peso por unidad: ${it.pesoPorUnidad} kg",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Emisiones de CO₂: ${it.emisionCO2Kilo} kg por kg",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}