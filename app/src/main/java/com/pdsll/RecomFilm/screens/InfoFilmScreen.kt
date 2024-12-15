package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.pdsll.RecomFilm.navigation.AppScreens
import com.pdsll.RecomFilm.screens.login.AuthenticationResult
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.viewinterop.AndroidView

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la función @Composable llamada NewPasswordScreen, que representa la pantalla para restablecer la contraseña.
// Permite al usuario ingresar su correo electrónico y enviar un enlace para restablecer la contraseña.
// También muestra un enlace para volver a la pantalla de inicio de sesión.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoFilmScreen(navController: NavHostController) {
    Scaffold {

        val nameFilm = "Barbie (2023)"
        val dateFilm = "07/20/2023"
        val directorFilm = "Greta Gerwig"
        val durationFilm = "1h 54m"
        val genreFilm = "Comedia, Aventura"
        val synopsisFilm = "Película basada en hechos reales del corredor de bolsa neoyorquino Jordan Belfort. A mediados de los años 80, Belfort era un joven honrado que perseguía el sueño americano, pero pronto en la agencia de valores aprendió que lo más importante no era hacer ganar a sus clientes, sino ser ambicioso y ganar una buena comisión. Su enorme éxito y fortuna le valió el mote de \"El lobo de Wall Street\". Dinero. Poder. Mujeres. Drogas. Las tentaciones abundaban y el temor a la ley era irrelevante. Jordan y su manada de lobos consideraban que la discreción era una cualidad anticuada; nunca se conformaban con lo que tenían."
        val ImageFilm = "https://www.themoviedb.org/t/p/w1280/fNtqD4BTFj0Bgo9lyoAtmNFzxHN.jpg"

        BodyInfoFilmScreen(navController, nameFilm, dateFilm, directorFilm, durationFilm, genreFilm, synopsisFilm, ImageFilm)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyInfoFilmScreen(
    navController: NavHostController,
    nameFilm: String,
    dateFilm: String,
    directorFilm: String,
    durationFilm: String,
    genreFilm: String,
    synopsisFilm: String,
    ImageFilm: String
) {

    // Nuevo estado para controlar la visibilidad del cuadro de diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Nuevo estado para el resultado de la autenticación
    var Result by remember { mutableStateOf(AuthenticationResult.None) }

    // Nuevo estado para manejar errores de inicio de sesión
    var infoText by remember { mutableStateOf<String?>(null) }

    val email = remember { mutableStateOf(TextFieldValue()) }

    IconButton(
        onClick = { navController.navigateUp() },
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nameFilm,
                style = TextStyle(
                    fontSize = 33.sp,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Agrega la imagen a la derecha del Row
            Image(
                painter = rememberImagePainter(ImageFilm),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Fecha de estreno: $dateFilm",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Duración: $durationFilm",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Director: $directorFilm",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = "Género: $genreFilm",
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = synopsisFilm,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                )
            )

//            YoutubePlayerComponent(youtubeVideoId = "KIssh5pZ42g")

        }
        Spacer(modifier = Modifier.width(20.dp))

//        // Agrega la imagen a la derecha del Row
//        Image(
//            painter = rememberImagePainter(ImageFilm),
//            contentDescription = null,
//            modifier = Modifier.size(200.dp)
//        )

    }

//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(40.dp),
////        verticalAlignment = Alignment.CenterVertically,
//        verticalAlignment = Alignment.Bottom,
////        horizontalArrangement = Arrangement.End
//    ) {
//    Text(
//        text = synopsisFilm,
//        style = TextStyle(
//            fontSize = 16.sp,
//            textAlign = TextAlign.Left
//        )
//    )
//    }
}


//@Composable
//fun BodyInfoFilmScreen(
//    navController: NavHostController,
//    nameFilm: String,
//    dateFilm: String,
//    directorFilm: String,
//    durationFilm: String,
//    genreFilm: String,
//    synopsisFilm: String,
//    ImageFilm: String
//) {
//
//    // Nuevo estado para controlar la visibilidad del cuadro de diálogo
//    var showDialog by remember { mutableStateOf(false) }
//
//    // Nuevo estado para el resultado de la autenticación
//    var Result by remember { mutableStateOf(AuthenticationResult.None) }
//
//    // Nuevo estado para manejar errores de inicio de sesión
//    var infoText by remember { mutableStateOf<String?>(null) }
//
//    val email = remember { mutableStateOf(TextFieldValue()) }
//
//    IconButton(
//        onClick = { navController.navigateUp() },
//        modifier = Modifier
//            .padding(16.dp)
//            .size(48.dp)
//            .clip(MaterialTheme.shapes.small)
//    ) {
//        Icon(Icons.Default.ArrowBack, contentDescription = null)
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(40.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Agrega la imagen al lado de la información de la película
//        Image(
//            painter = rememberImagePainter(ImageFilm),
//            contentDescription = null,
//            modifier = Modifier.size(200.dp)
//        )
//
//        Spacer(modifier = Modifier.width(20.dp))
//
//        Column(
//            modifier = Modifier.weight(1f),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = nameFilm,
//                style = TextStyle(
//                    fontSize = 33.sp,
//                    fontFamily = FontFamily.Cursive,
//                    textAlign = TextAlign.Center
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Fecha de estreno: $dateFilm",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Left
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Duración: $durationFilm",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Left
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Director: $directorFilm",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Left
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Género: $genreFilm",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Left
//                )
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = synopsisFilm,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Left
//                )
//            )
//        }
//    }
//}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BodyInfoFilmScreen(
//    navController: NavHostController,
//    nameFilm: String,
//    dateFilm: String,
//    directorFilm: String,
//    durationFilm: String,
//    genreFilm: String,
//    synopsisFilm: String,
//    ImageFilm: String
//    ) {
//
//    // Nuevo estado para controlar la visibilidad del cuadro de diálogo
//    var showDialog by remember { mutableStateOf(false) }
//
//    // Nuevo estado para el resultado de la autenticación
//    var Result by remember { mutableStateOf(AuthenticationResult.None) }
//
//    // Nuevo estado para manejar errores de inicio de sesión
//    var infoText by remember { mutableStateOf<String?>(null) }
//
//    val email = remember { mutableStateOf(TextFieldValue()) }
//
//    IconButton(
//        onClick = { navController.navigateUp() },
//        modifier = Modifier
//            .padding(16.dp)
//            .size(48.dp)
//            .clip(MaterialTheme.shapes.small)
//    ) {
//        Icon(Icons.Default.ArrowBack, contentDescription = null)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(40.dp),
//        verticalArrangement = Arrangement.Center,
////        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Text(
//            text = nameFilm,
//            style = TextStyle(
//                fontSize = 33.sp,
//                fontFamily = FontFamily.Cursive,
//                textAlign = TextAlign.Center
//            )
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Fecha de estreno: " + dateFilm,
//            style = TextStyle(
//                fontSize = 16.sp,
//                textAlign = TextAlign.Left
//            )
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Durecion: " + durationFilm,
//            style = TextStyle(
//                fontSize = 16.sp,
//                textAlign = TextAlign.Left
//            )
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Director: " + directorFilm,
//            style = TextStyle(
//                fontSize = 16.sp,
//                textAlign = TextAlign.Left
//            )
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Genero: " + genreFilm,
//            style = TextStyle(
//                fontSize = 16.sp,
//                textAlign = TextAlign.Left
//            )
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = synopsisFilm,
//            style = TextStyle(
//                fontSize = 16.sp,
//                textAlign = TextAlign.Left
//            )
//        )
//
////        Spacer(modifier = Modifier.height(20.dp))
////        Text(
////            text = "Ingresa tu correo electrónico para recibir un enlace de restablecimiento de contraseña",
////            style = TextStyle(
////                fontSize = 16.sp,
////                //fontFamily = FontFamily.Cursive
////            )
////        )
//
////        Spacer(modifier = Modifier.height(20.dp))
////        TextField(
////            label = { Text(text = "Correo electrónico") },
////            value = email.value,
////            onValueChange = { email.value = it })
//
////        Spacer(modifier = Modifier.height(20.dp))
////        Button(
////            onClick = { showDialog = true },
////            shape = RoundedCornerShape(50.dp),
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(50.dp)
////        ) {
////            Text(text = "Enviar correo electrónico")
////        }
//
//        // Muestra un cuadro de diálogo si showDialog es true
////        if (showDialog) {
////            AlertDialog(
////                onDismissRequest = { showDialog = false },
////                title = {
////                    Text("Contraseña restablecida")
////                },
////                text = {
////                    Text("Nueva contraseña enviada a su correo electrónico")
////                },
////                confirmButton = {
////                    Button(
////                        onClick = {
////                            showDialog = false
////                        },
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .height(50.dp),
////                    ) {
////                        Text("Aceptar")
////                    }
////                }
////            )
////        }
//
////        Spacer(modifier = Modifier.height(20.dp))
////        ClickableText(
////            text = AnnotatedString("Iniciar sesión"),
////            onClick = { navController.navigate(route = AppScreens.LoginScreen.route) },
////            style = TextStyle(
////                fontSize = 14.sp,
////                fontFamily = FontFamily.Default
////            )
////        )
//    }
//
//    // Muestra un mensaje de error si la autenticación falla
////    if (Result == AuthenticationResult.Failure) {
////        Spacer(modifier = Modifier.height(16.dp))
////        infoText?.let { error ->
////            Text(
////                text = error,
////                color = Color.Red
////            )
////        }
////    }
//
//}

@Composable
fun YoutubePlayerComponent(youtubeVideoId: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl("https://www.youtube.com/watch?v=$youtubeVideoId")
                }
            }
        )
    }
}

//@Composable
//fun YoutubePlayerComponent(youtubeVideoId: String) {
//    Box {
//        // Manejar la reproducción del video de YouTube
//        val intent = YouTubeStandalonePlayer.createVideoIntent(
//            context = null,
//            apiKey = "AIzaSyDxh6gKZij9Qj_emmQO9xYU3CmVYal_2vU", // Clave de API de YouTube
//            videoId = youtubeVideoId
//        )
//        // Abre la aplicación de YouTube con el video especificado
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//        val youtubeAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$youtubeVideoId"))
//        youtubeAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        val chooserIntent = Intent.createChooser(youtubeAppIntent, "Abrir con")
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intent))
//        // Iniciar la actividad
//        context.startActivity(chooserIntent)
//    }
//}
