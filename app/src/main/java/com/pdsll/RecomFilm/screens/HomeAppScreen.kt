package com.pdsll.RecomFilm.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.pdsll.RecomFilm.components.MenuLateral
import com.pdsll.RecomFilm.components.TopBar
import com.pdsll.RecomFilm.model.DataViewModel
import com.pdsll.RecomFilm.model.Items
import com.pdsll.RecomFilm.screens.login.UserCredentials
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la función @Composable llamada HomeAppScreen, que representa la pantalla principal de la aplicación.
// Utiliza un menú lateral y una barra de navegación en la parte superior.
// También muestra una lista de géneros y una cuadrícula de elementos que pueden ser filtrados por género.
// Los elementos en la cuadrícula muestran una imagen y el nombre del elemento.

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppScreen(navController: NavController, drawerState: DrawerState) {
    val dataViewModel = DataViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
    ) {
        TopBar(navController, drawerState)
        MenuLateral(navController, drawerState, dataViewModel)

    }
}

@Composable
fun GenreList(
    genres: List<String>,
    onGenreSelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color(106, 6, 186))
    ) {
        items(genres) { genre ->
            Text(
                text = genre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onGenreSelected(genre) }
            )
        }
    }
}

@Composable
fun TabItem(
    title: String,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector
) {
    Icon(
        imageVector = unSelectedIcon,
        contentDescription = title
    )
    Text(text = title)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeAppScreens(navController: NavController, drawerState: DrawerState) {
    val dataViewModel = DataViewModel()

    val tabItems = listOf(
        TabItem(
            title = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
        TabItem(
//            title = "Mis películas",
//            selectedIcon = Icons.Filled.Favorite,
//            unSelectedIcon = Icons.Outlined.FavoriteBorder
            title = "Buscador",
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search
        ),
        TabItem(
            title = "Perfil",
            selectedIcon = Icons.Filled.Face,
            unSelectedIcon = Icons.Outlined.Face
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { tabItems.size }
        var showDialog by remember { mutableStateOf(false) }
        var moviesString by remember { mutableStateOf("") }

        LaunchedEffect(key1 = selectedTabIndex) { pagerState.animateScrollToPage(selectedTabIndex) }
        LaunchedEffect(
            key1 = pagerState.currentPage,
            key2 = pagerState.isScrollInProgress
        ) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }

        Column(Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            when (index) {
                                0 -> {
                                    Text(text = "Inicio")
                                }

                                1 -> {
                                    Text(text = "Buscador")
                                }

                                2 -> {
                                    Text(text = "Perfil")
                                }
                            }
                        },
                        icon = {
                            when (index) {
                                0 -> {
                                    Icon(
                                        imageVector = if (index == selectedTabIndex) {
                                            Icons.Outlined.Home
                                        } else
                                            Icons.Filled.Home,
                                        contentDescription = ""
                                    )
                                }

                                1 -> {
                                    Icon(
//                                        imageVector = if (index == selectedTabIndex) {
//                                            Icons.Outlined.Favorite
//                                        } else
//                                            Icons.Filled.FavoriteBorder,
//                                        contentDescription = ""
                                        imageVector = if (index == selectedTabIndex) {
                                            Icons.Outlined.Search
                                        } else
                                            Icons.Filled.Search,
                                        contentDescription = ""
                                    )
                                }

                                2 -> {
                                    Icon(
                                        imageVector = if (index == selectedTabIndex) {
                                            Icons.Filled.Face
                                        } else
                                            Icons.Outlined.Face,
                                        contentDescription = ""
                                    )
                                }
                            }
                        },
                        selectedContentColor = Color(0xFF6650a4),
                        unselectedContentColor = Color(0xFF3F3268)
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                when (index) {
                    0 -> {
                        BodyHomeApp(navController, drawerState, dataViewModel)
                    }

                    1 -> {
                        DataScreen(navController)
                    }

                    2 -> {
                        BodyUserDataScreen(navController, drawerState, dataViewModel)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyHomeApp(
    navController: NavController,
    drawerState: DrawerState,
    dataViewModel: DataViewModel
) {
    var selectedGenre by remember { mutableStateOf("Recomendadas") }

    val getItems = dataViewModel.state.value
    var searchTerm by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("name") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Items?>(null) }

    val genres = listOf(
        "Recomendadas", "Acción", "Aventuras", "Ciencia Ficción",
        "Comedia", "Documentales", "Drama", "Musicales", "Suspense", "Terror"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF6650a4)),
        verticalArrangement = Arrangement.Top,
    ) {
        GenreList(genres = genres, onGenreSelected = { genre ->
            selectedGenre = genre

            if (genre == "Recomendadas") {
                dataViewModel.getData()
            } else {
                dataViewModel.searchData(genre, "genre")
            }
        })

        LazyVerticalGrid(
            GridCells.Fixed(3),
            modifier = Modifier
                .background(color = Color(0xFF6650a4)),
        ) {
            items(getItems) { item ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    onClick = {
                        selectedItem = item
                        showDialog = true
                    }) {
                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .background(color = Color(0xFF6650a4)),
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = item.image),
                            contentDescription = null,
                            //contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .width(900.dp)
                        )
                        Text(
                            item.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }
    }

    if (showDialog && selectedItem != null) {
        MovieInfoDialog(
            item = selectedItem!!,
            onDismiss = { showDialog = false },
            onAction = { movieName ->
            }
        )
    }
}

@Composable
fun MovieInfoDialog(item: Items, onDismiss: () -> Unit, onAction: (Any?) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = onDismiss
            ) {
                Text("Cerrar")
            }
        },
        title = {
            Text(text = item.name, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = item.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                MovieDetailText(label = "Fecha", detail = item.date)
                MovieDetailText(label = "Director", detail = item.director)
                MovieDetailText(label = "Género", detail = item.genre)
                MovieDetailText(label = "Duración", detail = item.duration)
                MovieDetailText(label = "Sinopsis", detail = item.synopsis)

                Button(
                    onClick = {
                        val correoUser = UserCredentials.username
                        addMovieToUser(correoUser, item.name)
                        runBlocking {
                            val iduser = getUserIdByEmail(correoUser)
                            if (iduser != null) {
                                addMovieToUser(iduser, item.name)
                            }
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Añadir a \"Mis películas\"")
                }

            }
        }
    )
}

// Debes tener una instancia de FirebaseFirestore en tu archivo Kotlin
val db = FirebaseFirestore.getInstance()

suspend fun getUserIdByEmail(email: String): String? {
    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("users")

    return try {
        val querySnapshot = usersCollection.whereEqualTo("email", email).get().await()
        if (!querySnapshot.isEmpty) {
            // Si encontramos el documento del usuario, obtenemos su ID
            val userId = querySnapshot.documents[0].id
            userId
        } else {
            null // No se encontró ningún usuario con el correo electrónico
        }
    } catch (e: Exception) {
        println("Error al buscar usuario por correo electrónico: $email, Error: $e")
        null
    }
}

fun addMovieToUser(userId: String, movieName: String) {
    // Obtenemos la referencia al documento del usuario
    val userRef = db.collection("users").document(userId)

    // Actualizamos los datos del usuario añadiendo la película al array de películas
    userRef.update("movies", FieldValue.arrayUnion(movieName))
        .addOnSuccessListener {
            println("Película añadida correctamente al usuario: $userId")
        }
        .addOnFailureListener { e ->
            println("Error al añadir película al usuario: $userId, Error: $e")
        }
}

@Composable
fun MovieDetailText(label: String, detail: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) { // Espacio entre cada par de label y detail
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp) // Espacio entre la etiqueta y el detalle
        )
        Text(text = detail)
    }
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