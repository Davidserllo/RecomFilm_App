package com.pdsll.RecomFilm.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.navigation.AppScreens
import com.pdsll.RecomFilm.screens.login.LoginScreenViewModel

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la función @Composable llamada NewUserScreen, que representa la pantalla para que un nuevo usuario cree una cuenta.
// Permite al usuario ingresar su información, incluido un correo electrónico, nombre de usuario y contraseña.
// También permite al usuario seleccionar una imagen de perfil.
// Después de ingresar la información requerida y hacer clic en el botón "Crear usuario", se verifica si las contraseñas coinciden.
// Si coinciden, se llama a la función viewModel.createUserWithEmailAndPassword() para crear el usuario en Firebase Auth.
// También muestra un cuadro de diálogo para informar al usuario sobre el resultado de la creación del usuario.
// Si las contraseñas no coinciden, muestra un cuadro de diálogo de error.
// Además, hay un enlace para que el usuario inicie sesión si ya tiene una cuenta.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewUserScreen(navController: NavHostController, viewModel: LoginScreenViewModel) {
    Scaffold {
        BodyNewUserScreen(navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyNewUserScreen(navController: NavHostController, viewModel: LoginScreenViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    IconButton(
        onClick = { navController.navigateUp() },
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val email = remember { mutableStateOf(TextFieldValue()) }
            val username = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }
            val passwordconfirmation = remember { mutableStateOf(TextFieldValue()) }

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
                text = "Nuevo usuario",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Correo electrónico") },
                value = email.value,
                onValueChange = { email.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Nombre de usuario") },
                value = username.value,
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Contraseña") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Repita la Contraseña") },
                value = passwordconfirmation.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { passwordconfirmation.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        val username = username.value.text
                        val email = email.value.text
                        val pass = password.value.text
                        val pass2 = passwordconfirmation.value.text

                        if (pass == pass2) {
                            viewModel.createUserWithEmailAndPassword(email, pass, username, emptyList()) {
                                showDialog = true
                                dialogTitle = "Usuario creado"
                                dialogMessage = "Usuario nuevo creado correctamente"

                                val usernameText = ""
                                val emailText = ""
                                val passwordText = ""
                                val passwordConfirmationText = ""
                            }
                        } else {
                            showDialog = true
                            dialogTitle = "Error"
                            dialogMessage =
                                "Las contraseñas no coinciden. Por favor, vuelve a intentarlo."
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Crear usuario")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString("Iniciar sesión"),
                onClick = { navController.navigate(route = AppScreens.LoginScreen.route) },
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )
        }
    }

    ShowDialog(
        showDialog,
        dialogTitle,
        dialogMessage,
        onDialogDismiss = { showDialog = false },
        onAccept = {
            navController.navigate(route = AppScreens.LoginScreen.route)
        }
    )
}

    @Composable
fun ShowDialog(
    showDialog: Boolean,
    dialogTitle: String,
    dialogMessage: String,
    onDialogDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDialogDismiss,
            title = {
                dialogTitle.let { Text(it) }
            },
            text = {
                dialogMessage.let { Text(it) }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDialogDismiss()
                        onAccept()
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
}
