package com.example.reciclapp.presentation.ui.login.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reciclapp.R
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.presentation.ui.menu.ui.vistas.components.HeaderImageLogoReciclapp
import com.example.reciclapp.presentation.ui.menu.ui.vistas.components.LoadingButton
import com.example.reciclapp.presentation.ui.registro.ui.showToast
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavHostController) {
    val loginState by loginViewModel.loginState.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LoginContent(loginViewModel, navController, loginState)
    }
}

@Composable
fun LoginContent(
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    loginState: Result<Usuario>?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val email by loginViewModel.email.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    val loginEnable by loginViewModel.loginEnable.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImageLogoReciclapp(Modifier.size(width = 400.dp, height = 300.dp))
        Spacer(modifier = Modifier.height(16.dp))
        LoginTextField("Email", email, KeyboardType.Email) {
            loginViewModel.onLoginChanged(
                it,
                password
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LoginTextField(
            "Contraseña",
            password,
            KeyboardType.Password,
            isPassword = true
        ) { loginViewModel.onLoginChanged(email, it) }
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPasswordButton(loginViewModel)
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(
            loginViewModel,
            loginEnable
        ) { coroutineScope.launch { loginViewModel.onLoginSelected() } }
        Spacer(modifier = Modifier.height(16.dp))
        OrSeparator()
        Spacer(modifier = Modifier.height(5.dp))
        SocialLoginButton("Continuar con Google", R.drawable.crome)
        Spacer(modifier = Modifier.height(16.dp))
        AccountNoButton(navController)

        loginState?.let { result ->
            when {
                result.isSuccess -> {
                    showToast(context, "Bienvenido")
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                result.isFailure -> showToast(context, "Ingreso fallido")
            }
            loginViewModel.resetState()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        painter = painterResource(
                            id =
                            if (passwordVisibility) R.drawable.invisible
                            else R.drawable.ojo
                        ),
                        contentDescription =
                        if (passwordVisibility)
                            "Ocultar contraseña"
                        else "Mostrar contraseña"
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@Composable
fun ForgotPasswordButton(loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    Text(
        text = "Olvidaste la contraseña?",
        modifier = Modifier.clickable {
            loginViewModel.onForgotPassword()
            showToast(context, "Olvidaste la contraseña?")
        },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoginButton(loginViewModel: LoginViewModel, loginEnable: Boolean, onLoginSelected: () -> Unit) {
    val isLoading by loginViewModel.isLoading.observeAsState(false)
    LoadingButton(isLoading, "Iniciar sesión", onLoginSelected, loginEnable)
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
fun SocialLoginButton(text: String, iconRes: Int) {
    Button(
        onClick = { /* Acción de login social */ },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color.LightGray)
                ),
                shape = RoundedCornerShape(30.dp)
            ),
        border = BorderStroke(0.5.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = text, color = Color.Black, fontSize = 16.sp)
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )
        }
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
