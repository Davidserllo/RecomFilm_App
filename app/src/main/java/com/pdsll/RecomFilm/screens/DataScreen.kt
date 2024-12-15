package com.pdsll.RecomFilm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.pdsll.RecomFilm.model.DataViewModel
import com.pdsll.RecomFilm.model.Items
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    navController: NavController,
    dataViewModel: DataViewModel = viewModel()
) {
    val getItems = dataViewModel.state.value

    var searchTerm by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("name") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Items?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(color = Color.Black, width = 1.dp)
                    .padding(5.dp, 0.dp, 5.dp, 8.dp,)
            ) {
                OutlinedTextField(
                    value = searchTerm,
                    onValueChange = {
                        searchTerm = it
                        if (it.isEmpty()) {
                            dataViewModel.getData()
                        } else {
                            dataViewModel.searchData(it, selectedOption)
                        }
                    },
                    label = { Text("Busca pelicula...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done
                    ),
                    trailingIcon = {
                        Column {
                            Text(selectedOption, fontWeight = FontWeight.Bold)
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    dropdownExpanded = true
                                }
                            )
                        }
                    }
                )
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Busqueda por nombre") },
                    onClick = {
                        selectedOption = "name"
                        dropdownExpanded = false
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(color = if (selectedOption == "name") Color.White else Color.Transparent)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(top = 70.dp)
        ) {
            items(getItems) { getItems ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(color = Color(0xFF6650a4)),
                    onClick = {
                        selectedItem = getItems
                        showDialog = true
                    }) {
                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .background(color = Color(0xFF6650a4)),
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = getItems.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(150.dp)
                                .width(900.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                        )

                        Text(
                            getItems.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.White,
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

    if (showDialog && selectedItem != null) {
        MovieInfoDialog(
            item = selectedItem!!,
            onDismiss = { showDialog = false },
            onAction = { movieName ->
            }
        )
    }
}

// Debes tener una instancia de FirebaseFirestore en tu archivo Kotlin
val dbs = FirebaseFirestore.getInstance()

suspend fun getUserMovies(userId: String): List<String> {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    return try {
        val documentSnapshot = userRef.get().await()
        if (documentSnapshot.exists()) {
            // Obtener el array de películas del documento del usuario
            val movies = documentSnapshot.get("movies") as? List<String>
            movies ?: emptyList() // Devuelve la lista de películas o una lista vacía si es null
        } else {
            emptyList() // Si el documento no existe, devuelve una lista vacía
        }
    } catch (e: Exception) {
        println("Error al obtener las películas del usuario: $userId, Error: $e")
        emptyList() // En caso de error, devuelve una lista vacía
    }
}
