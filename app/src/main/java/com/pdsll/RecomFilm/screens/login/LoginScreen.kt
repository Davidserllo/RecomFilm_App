package com.pdsll.RecomFilm.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.navigation.AppScreens

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta función composable llamada BodyLoginScreen representa la interfaz de usuario de la pantalla de inicio de sesión.
// Incluye campos de entrada para el nombre de usuario y la contraseña, un botón para iniciar sesión y enlaces para registrar un nuevo usuario y restablecer la contraseña.
// También maneja la visibilidad de la contraseña, muestra un diálogo de error si las credenciales son incorrectas y muestra un mensaje de error si la autenticación falla.
// Utiliza un ViewModel llamado LoginScreenViewModel para manejar la lógica de inicio de sesión.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginScreenViewModel) {
    Scaffold {
        BodyLoginScreen(navController, viewModel)
    }
}

enum class AuthenticationResult {
    None,
    Success,
    Failure
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyLoginScreen(navController: NavHostController, viewModel: LoginScreenViewModel) {

    // Nuevo estado para el nombre de usuario y la contraseña
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    // Nuevo estado para controlar la visibilidad del cuadro de diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Nuevo estado para el resultado de la autenticación
    var authenticationResult by remember { mutableStateOf(AuthenticationResult.None) }

    // Nuevo estado para manejar errores de inicio de sesión
    var errorText by remember { mutableStateOf<String?>(null) }

    Spacer(modifier = Modifier.height(20.dp))

    Box(modifier = Modifier.fillMaxSize()) {

        ClickableText(
            text = AnnotatedString("Registrate aquí"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(45.dp),
            onClick = { navController.navigate(route = AppScreens.NewUserScreen.route) },
            style = TextStyle(
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        )

        ClickableText(
            text = AnnotatedString("Términos y Política de privacidad"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { navController.navigate(route = AppScreens.PrivacyPolicyScreen.route) },
            style = TextStyle(
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline
            )
        )

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }


        Image(
            painter = painterResource(id = R.drawable.recomfilm_icono_name),
            contentDescription = "RecomFilm",
            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Inicia sesión",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Correo electronico") },
            value = username.value,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = "Contraseña") },
            value = password.value,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Lock else Icons.Default.Edit,
                        contentDescription = null,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    val user = username.value.text
                    val pass = password.value.text

                    UserCredentials.username = username.value.text
//                    UserCredentials.password = password.value.text

                    viewModel.signInWithEmailAndPassword(user, pass) { loginSuccess ->
                        if (loginSuccess) {
                            navController.navigate(route = AppScreens.HomeAppScreen.route)
                        } else {
                            // Credenciales incorrectas, muestra un cuadro de diálogo
                            showDialog = true
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar sesión")
            }
        }

        // Muestra un cuadro de diálogo si showDialog es true
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text("Error de autenticación")
                },
                text = {
                    Text("Credenciales incorrectas. Por favor, inténtalo de nuevo.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Has olvidado tu contraseña?"),
            onClick = { navController.navigate(route = AppScreens.NewPasswordScreen.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Contacto"),
            onClick = { navController.navigate(route = AppScreens.InfoRecomFilm.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )

    }

    // Muestra un mensaje de error si la autenticación falla
    if (authenticationResult == AuthenticationResult.Failure) {
        Spacer(modifier = Modifier.height(16.dp))
        errorText?.let { error ->
            Text(
                text = error,
                color = Color.Red
            )
        }
    }
}

object UserCredentials {
    var username: String = "Nombre de usuario"
//    var password: String = "Correo electronico"
}

val username = UserCredentials.username
//val password = UserCredentials.password