package com.pdsll.RecomFilm.components

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.screens.getUserIdByEmail
import com.pdsll.RecomFilm.screens.getUserMovies
import com.pdsll.RecomFilm.screens.login.UserCredentials
import kotlinx.coroutines.launch

// Se define el composable TopBar que representa la barra superior de la aplicación.
// La barra incluye un título, un botón de menú que abre o cierra el cajón lateral y un botón de búsqueda.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    drawerState: DrawerState
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    var moviesString by remember { mutableStateOf("") }

    CenterAlignedTopAppBar(
        title = {
            Icon(
                painter = painterResource(id = R.drawable.recomfilm_name),
                contentDescription = "Recom Film",
                tint = Color(0xFF6A06BA)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color(0xFF6A06BA)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    // imageVector = Icons.Default.Search,
                    painter = painterResource(id = R.drawable.heart_load_icon),
                    contentDescription = "Mis películas",
                    tint = Color(0xFF6A06BA)
                )
            }
        }
    )

    if (showDialog) {
        UserMoviesDialog(
            userEmail = UserCredentials.username,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun UserMoviesDialog(userEmail: String, onDismiss: () -> Unit) {
    var moviesString by remember { mutableStateOf("") }

    LaunchedEffect(userEmail) {
        val iduser = getUserIdByEmail(userEmail)
        if (iduser != null) {
            val userMovies = getUserMovies(iduser)
            moviesString = userMovies.joinToString(separator = "\n") { it }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cerrar")
            }
        },
        title = {
            Text("Mis películas")
        },
        text = {
            Column {
                Text(moviesString)
            }
        }
    )
}

//@Composable
//fun showDialogWithMovies(movies: String, onDismiss: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            Button(
//                onClick = onDismiss
//            ) {
//                Text("Cerrar")
//            }
//        },
//        title = {
//            Text("Mis películas")
//        },
//        text = {
//            Column {
//                Text(movies)
//            }
//        }
//    )
//}