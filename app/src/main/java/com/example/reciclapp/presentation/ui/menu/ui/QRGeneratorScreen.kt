package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.google.gson.JsonObject
import java.time.LocalDateTime
import java.util.UUID
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.presentation.viewmodel.TransaccionViewModel
import com.google.gson.JsonParser
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QRGeneratorScreen(
    producto: ProductoReciclable,
    usuarioContactado: Usuario,
    viewModel: TransaccionViewModel = hiltViewModel(),
    onQRGenerated: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val transaccionInfo = remember {
        JsonObject().apply {
            val idTransaccion = UUID.randomUUID().toString()
            addProperty("idTransaccion", idTransaccion)
            addProperty("idProducto", producto.idProducto)
            addProperty("idVendedor", producto.idVendedor)
            addProperty("idComprador", usuarioContactado.idUsuario)
            addProperty("fecha", LocalDateTime.now().toString())
        }.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Código QR para ${producto.nombreProducto}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        QRCode(
            content = transaccionInfo,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Text(
            text = "Guarda este código QR y muéstralo al ${if (producto.idVendedor.isEmpty()) "vendedor" else "comprador"}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                val transaccion = TransaccionPendiente(
                    idTransaccion = JsonParser.parseString(transaccionInfo)
                        .asJsonObject["idTransaccion"].asString,
                    idProducto = producto.idProducto,
                    idVendedor = producto.idVendedor,
                    idComprador = usuarioContactado.idUsuario,
                    fechaCreacion = LocalDateTime.now().toString(),
                    codigoQR = transaccionInfo
                )
                viewModel.crearTransaccionPendiente(transaccion)
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar transacción pendiente")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Transacción guardada") },
            text = { Text("La transacción ha sido guardada exitosamente. Puedes proceder a contactar al usuario.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onQRGenerated()
                    }
                ) {
                    Text("Continuar")
                }
            }
        )
    }
}
@Composable
fun QRCode(content: String, modifier: Modifier = Modifier) {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "QR Code",
        modifier = modifier.size(300.dp)
    )
}