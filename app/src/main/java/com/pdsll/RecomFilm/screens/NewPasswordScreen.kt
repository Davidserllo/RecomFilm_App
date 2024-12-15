package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.navigation.AppScreens
import com.pdsll.RecomFilm.screens.login.AuthenticationResult

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la función @Composable llamada NewPasswordScreen, que representa la pantalla para restablecer la contraseña.
// Permite al usuario ingresar su correo electrónico y enviar un enlace para restablecer la contraseña.
// También muestra un enlace para volver a la pantalla de inicio de sesión.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPasswordScreen(navController: NavHostController) {
    Scaffold {
        BodyNewPasswordScreen(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyNewPasswordScreen(navController: NavHostController) {

    // Nuevo estado para controlar la visibilidad del cuadro de diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Nuevo estado para el resultado de la autenticación
    var Result by remember { mutableStateOf(AuthenticationResult.None) }

    // Nuevo estado para manejar errores de inicio de sesión
    var infoText by remember { mutableStateOf<String?>(null) }

    val email = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.small)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(40.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp)) // Baja la imagen un poco

                Image(
                    painter = painterResource(id = R.drawable.recomfilm_icono_name),
                    contentDescription = "RecomFilm",
                    modifier = Modifier
                        .height(100.dp)
                        .width(200.dp)
                        .background(Color.Transparent),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Ingresa tu correo electrónico para recibir un enlace de restablecimiento de contraseña",
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Correo electrónico") },
                    value = email.value,
                    onValueChange = { email.value = it })

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Enviar correo electrónico")
                }

                // Muestra un cuadro de diálogo si showDialog es true
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = {
                            Text("Contraseña restablecida")
                        },
                        text = {
                            Text("Nueva contraseña enviada a su correo electrónico")
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
                    text = AnnotatedString("Iniciar sesión"),
                    onClick = { navController.navigate(route = AppScreens.LoginScreen.route) },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default
                    )
                )

                // Muestra un mensaje de error si la autenticación falla
                if (Result == AuthenticationResult.Failure) {
                    Spacer(modifier = Modifier.height(16.dp))
                    infoText?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    )
}
