package com.example.reciclapp.presentation.ui.login.ui


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val loginState by viewModel.loginState.observeAsState()

    if (isLoading) {
        LoadingIndicator()
    } else {
        LoginContent(viewModel, navController, loginState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(viewModel: LoginViewModel, navController: NavHostController, loginState: Result<Usuario>?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val loginEnable by viewModel.loginEnable.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage()
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(email) { viewModel.onLoginChanged(it, password) }
        Spacer(modifier = Modifier.height(4.dp))
        PasswordField(password) { viewModel.onLoginChanged(email, it) }
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPasswordButton(viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(loginEnable) {
            coroutineScope.launch { viewModel.onLoginSelected(email, password) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OrSeparator()
        Spacer(modifier = Modifier.height(5.dp))
        SocialLoginButtons(viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        AccountNoButton(navController)

        loginState?.let { result ->
            when {
                result.isSuccess -> {
                    showToast(context, "Bienvenido")
                    navController.navigate("menu")
                    viewModel.resetState()
                }
                result.isFailure -> {
                    showToast(context, "Ingreso fallido")
                }
            }
        }
    }
}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = Modifier
            .size(width = 500.dp, height = 300.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onValueChange: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(id = if (passwordVisibility) R.drawable.invisible else R.drawable.ojo),
                    contentDescription = if (passwordVisibility) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@Composable
fun ForgotPasswordButton(viewModel: LoginViewModel) {
    val context = LocalContext.current
    Text(
        text = "Olvidaste la contraseña?",
        modifier = Modifier.clickable {
            viewModel.onForgotPassword()
            showToast(context, "Olvidaste la contraseña?")
        },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = onLoginSelected,
        enabled = loginEnable,
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp),
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF41A829),
            disabledContainerColor = Color(0xFF174D11),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OrSeparator() {
    Text(
        text = "----Or----",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SocialLoginButtons(viewModel: LoginViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SocialLoginButton(R.drawable.facebook, "Iniciar sesión con Facebook") { viewModel.loginWithFacebook() }
        SocialLoginButton(R.drawable.crome, "Iniciar sesión con Chrome") { viewModel.loginWithChrome() }
    }
}

@Composable
fun SocialLoginButton(iconId: Int, toastMessage: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
            showToast(context,toastMessage)
        }
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = toastMessage,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun AccountNoButton(navController: NavHostController) {
    Text(
        text = "No tienes cuenta? Registrate",
        modifier = Modifier.clickable {
            navController.navigate("registro")
        },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
