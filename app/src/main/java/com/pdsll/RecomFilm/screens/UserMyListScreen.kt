package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pdsll.RecomFilm.model.DataViewModel
import com.pdsll.RecomFilm.screens.login.UserCredentials

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMyListScreen(
    navController: NavController,
    drawerState: DrawerState,
) {
    val dataViewModel = DataViewModel()
    Scaffold {
        BodyUserDataScreen(
        navController,
        drawerState,
        dataViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyUserMyListScreen(
    navController: NavController,
    drawerState: DrawerState,
    dataViewModel: DataViewModel
) {
    val getItems = dataViewModel.state.value

    // Estado para mostrar notificaciones
    val notification = rememberSaveable { mutableStateOf("") }
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    // Estado para almacenar el nombre, email y bio
    var name by rememberSaveable { mutableStateOf("default name") }
    var username by rememberSaveable { mutableStateOf("default username") }

    val correoUser = UserCredentials.username

    // Obtener la película favorita del usuario
//    val userEmail = UserCredentials.username
    val filmFavorites = remember { mutableStateOf(listOf<String>()) }
    val displayName = remember { mutableStateOf("") }

    LaunchedEffect(correoUser) {
        displayName.value = dataViewModel.getDisplayNameByEmailFromFirestore(correoUser) ?: ""
    }

    LaunchedEffect(correoUser) {
        filmFavorites.value = dataViewModel.getFilmFavoritesByEmailFromFirestores(correoUser)
    }

    val film = filmFavorites.value.joinToString()
    dataViewModel.searchData(film,"name")

        // Campo de nombre
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Nombre",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6650a4)
                )
            )

            // Mostrar el display_Name en un TextField
            TextField(
                value = displayName.value,
                onValueChange = { /* No permitir cambios */ },
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Correo electronico",

                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6650a4),

                )
            )

            TextField(
                value = correoUser,
                onValueChange = { /*No se puedo cambiar*/},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Pelicula favorita",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    color = Color(0xFF6650a4),

                    )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de películas
            val movies = dataViewModel.state.value

            LazyVerticalGrid(
                GridCells.Fixed(1),
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
            ) {
                items(movies) { movie ->
                    Button(
                        modifier = Modifier
                            .width(300.dp)
                            .height(250.dp)
                            .background(color = Color(0xFF6650a4)),
                        onClick = { /*ItemScreen(viewModel())*/ }) {
                        Column(
                            modifier = Modifier
                                .height(250.dp)
                                .background(color = Color(0xFF6650a4)),
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = movie.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .padding(10.dp)
                            )
                            Text(
                                movie.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }