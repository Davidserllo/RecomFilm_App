package com.pdsll.RecomFilm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pdsll.RecomFilm.navigation.AppNavigation
import com.pdsll.RecomFilm.ui.theme.LoginForm_PDSLLTheme

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la clase principal MainActivity de la aplicación.
// Se configura el contenido de la actividad para utilizar Jetpack Compose.
// Se crea un Surface que actúa como contenedor principal de la interfaz de usuario.
// Se utiliza rememberNavController para recordar el NavController de la aplicación.
// Se llama a la función AppNavigation para configurar la navegación en la aplicación.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginForm_PDSLLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                AppNavigation()
                }
            }
        }
    }
}