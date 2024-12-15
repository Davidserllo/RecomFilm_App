package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.pdsll.RecomFilm.R
import com.google.maps.android.compose.*

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
fun InfoRecomFilm(navController: NavHostController) {
    Scaffold {
        BodyInfoRecomFilm(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyInfoRecomFilm(navController: NavHostController) {
    // Estado para controlar si se muestra el mapa
    var showMap by remember { mutableStateOf(false) }

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
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Mostrar la información de contacto y el logo
                item {
                    Image(
                        painter = painterResource(id = R.drawable.recomfilm_icono_name),
                        contentDescription = "RecomFilm Logo",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp)
                    )

                    Text(
                        text = "RecomFilm",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "recomfilm@gmail.com\n+34 667766000",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Botón para mostrar u ocultar el mapa
                item {
                    Button(
                        onClick = { showMap = !showMap },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(48.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = if (showMap) "Ocultar Mapa" else "Oficinas RecomFilm"
                        )
                    }
                }

                // Mostrar el mapa si el estado es true
                if (showMap) {
                    item {
                        MyGoogleMaps()
                    }
                }
            }
        }
    )
}

@Composable
fun MyGoogleMaps() {
    val marker = LatLng(39.6713531,-0.2467213)
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.HYBRID)) }

    // Ajusta el tamaño del contenedor del mapa utilizando el modificador size
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(420.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties, // tipo de terreno
        ) {
            Marker(position = marker, title = "RecomFilm", snippet = "   🏢🖥️🏢")
        }
    }
}

/* android:value="AIzaSyDJUHzQWPBCqE3aRqCZ9-Yzhp-ma716C_o"/>  Clave Mario */
/* android:value="AIzaSyCNfM4EvrpGXCkFAIvmJxeFniqeM7DOEFU"/>  Clave Javi */