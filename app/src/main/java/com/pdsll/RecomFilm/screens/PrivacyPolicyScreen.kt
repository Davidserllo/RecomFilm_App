package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdsll.RecomFilm.R


/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la pantalla de política de privacidad, que muestra diferentes secciones de información sobre la política de privacidad de la aplicación.
// Utiliza LazyColumn para organizar y mostrar el contenido de manera eficiente.
// Cada sección comienza con un título en negrita y se siguen de párrafos de texto explicativo.
// Al final, se incluye un botón de flecha hacia atrás para permitir al usuario navegar hacia atrás.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    Scaffold {
        BodyPrivacyPolicyScreen(navController)
    }
}

// Función items personalizada para LazyColumn
fun <T> LazyListScope.items(
    items: List<T>,
    itemContent: @Composable (item: T) -> Unit
) {
    items.forEach { item ->
        item {
            itemContent(item)
        }
    }
}

@Composable
fun BodyPrivacyPolicyScreen(navController: NavHostController) {
    LazyColumn {
        item {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }

        item {
            Image(
                painter = painterResource(id = R.drawable.recomfilm_name),
                contentDescription = "RecomFilm",
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp)
                    .background(Color.Transparent),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = "Términos y Política de Privacidad",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.
                    padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive
            )
        }

        items(informacionBienvenidaData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.
                padding(16.dp),
                fontSize = 16.sp
            )
        }

        // Sección 1: Información Recopilada
        item {
            Text(
                text = "1. Información Recopilada",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(informacionRecopiladaData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.
                    padding(16.dp),
                    fontSize = 16.sp
            )
        }

        // Sección 2: Uso de la Información
        item {
            Text(
                text = "2. Uso de la Información",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(usoInformacionData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Sección 3: Compartir Información
        item {
            Text(
                text = "3. Compartir Información",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(compartirInformacionData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Sección 4: Seguridad de la Información
        item {
            Text(
                text = "4. Seguridad de la Información",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(seguridadInformacionData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        // 5. Cambios en la Política de Privacidad
        item {
            Text(
                text = "5. Cambios en la Política de Privacidad",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(cambiosPoliticaPrivacidadData) { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

// Datos de ejemplo para las secciones
val informacionBienvenidaData = listOf(
    "Bienvenido a Recom Film, una aplicación móvil diseñada para ofrecerte" +
    "recomendaciones personalizadas de películas y series según tus preferencias y " +
    "hábitos de visualización."
)

val informacionRecopiladaData = listOf(
    "Recopilamos la siguiente información personal de nuestros usuarios:",
    "- Información de cuenta: Recopilamos tu nombre de usuario, dirección de correo electrónico y contraseña para crear y gestionar tu cuenta en Recom Film.",
    "- Datos de uso: Registramos información sobre tu actividad en la aplicación, como las películas y series que has visto, las clasificaciones que has proporcionado y cualquier interacción que realices dentro de la plataforma.",
    "- Información del dispositivo: Recopilamos datos sobre el dispositivo que utilizas para acceder a Recom Film, incluyendo el modelo del dispositivo, la versión del sistema operativo y la identificación única del dispositivo."
)

val usoInformacionData = listOf(
    "Utilizamos la información recopilada para los siguientes propósitos:",
    "- Personalización de recomendaciones: Utilizamos tus datos de uso y preferencias para ofrecerte recomendaciones personalizadas de películas y series que creemos que serán de tu interés.",
    "- Mejora de la experiencia del usuario: Analizamos los datos de uso para comprender mejor cómo los usuarios interactúan con la aplicación y realizar mejoras continuas en la interfaz y las funciones de Recom Film.",
    "- Comunicación y notificaciones: Utilizamos tu dirección de correo electrónico para enviarte comunicaciones relacionadas con tu cuenta, actualizaciones de la aplicación y notificaciones sobre nuevas recomendaciones."
)

val compartirInformacionData = listOf(
    "No compartiremos tu información personal con terceros sin tu consentimiento expreso, excepto cuando sea necesario para cumplir con la ley o proteger nuestros derechos legales."
)

// Datos de ejemplo para las secciones
val seguridadInformacionData = listOf(
    "Tomamos medidas para proteger la seguridad de tu información personal y garantizar su confidencialidad. Utilizamos protocolos de seguridad estándar de la industria al procesar y almacenar datos."
)

val cambiosPoliticaPrivacidadData = listOf(
    "Nos reservamos el derecho de actualizar esta política de privacidad en cualquier momento. Te notificaremos sobre cambios significativos a través de la aplicación " +
            "o por correo electrónico. Al utilizar Recom Film, aceptas los términos y condiciones descritos en esta política de privacidad. Si tienes alguna pregunta " +
            "o inquietud sobre la recopilación y el uso de tu información personal, no dudes en ponerte en contacto con nosotros a través de \"info@account.recomfilm.com.\""
)