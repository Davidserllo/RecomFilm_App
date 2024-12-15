package com.pdsll.RecomFilm.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.navigation.AppScreens
import kotlinx.coroutines.delay

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la pantalla de splash de RecomFilm, que se muestra al iniciar la aplicación.
// Utiliza animaciones para escalar el logotipo de la aplicación y luego navega a la pantalla de inicio de sesión después de un breve retraso.
// La pantalla de splash muestra el nombre de la aplicación, el logotipo y un mensaje breve.
// También verifica si el usuario está autenticado previamente y lo dirige a la pantalla de inicio correspondiente.

@Composable
fun RecomFilmSplashScreen(navController: NavHostController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            ),

            )
        delay(500L)
        navController.navigate(route = AppScreens.LoginScreen.route)
    }
    val color = MaterialTheme.colorScheme.primary
    Surface (modifier = Modifier
        .padding(15.dp)
        .size(330.dp)
        .scale(scale.value),
        shape = CircleShape,
//        color = Color(0xFFe6e9ef),
        border = BorderStroke(width = 2.dp, color = Color.White )
    ){
        Column (modifier = Modifier
            .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.recomfilm_icono_name),
                contentDescription = "RecomFilm",
                modifier = Modifier
                    .height(200.dp)
                    .width(400.dp)
                    .background(Color.Transparent),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Tus peliculas favoritas",
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                fontSize = 24.sp,
                color = color
            )
        }
    }
}