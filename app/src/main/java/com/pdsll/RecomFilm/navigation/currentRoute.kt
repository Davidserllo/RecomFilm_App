package com.pdsll.RecomFilm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

// Esta función composable llamada currentRoute toma un NavController como argumento y devuelve la ruta actual de la pantalla.
// Devuelve la ruta actual como una cadena.

@Composable
fun currentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route