package com.pdsll.RecomFilm.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pdsll.RecomFilm.model.DataViewModel
import com.pdsll.RecomFilm.screens.DataScreen
import com.pdsll.RecomFilm.screens.HomeAppScreen
import com.pdsll.RecomFilm.screens.InfoRecomFilm
import com.pdsll.RecomFilm.screens.login.LoginScreen
import com.pdsll.RecomFilm.screens.NewPasswordScreen
import com.pdsll.RecomFilm.screens.NewUserScreen
import com.pdsll.RecomFilm.screens.PrivacyPolicyScreen
import com.pdsll.RecomFilm.screens.RecomFilmSplashScreen
import com.pdsll.RecomFilm.screens.UserDataScreen
import com.pdsll.RecomFilm.screens.login.LoginScreenViewModel
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import com.pdsll.RecomFilm.screens.InfoFilmScreen
import com.pdsll.RecomFilm.screens.UserMyListScreen

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Se define la función AppNavigation que gestiona la navegación dentro de la aplicación.
// Esta función utiliza el componente NavHost de Jetpack Compose para definir las distintas pantallas y rutas de la aplicación.
// Cada pantalla se asocia con su respectivo composable mediante la función composable.
// Se utiliza rememberNavController para obtener el controlador de navegación.
// El startDestination se establece en la pantalla de SplashScreen.
// Se incluyen las siguientes pantallas: LoginScreen, NewUserScreen, NewPasswordScreen, HomeAppScreen, UserDataScreen, PrivacyPolicyScreen, RecomFilmSplashScreen y DataScreen.

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
        val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed)

    NavHost(navController = navController, startDestination = AppScreens.RecomFilmSplashScreen.route) {

        composable(route = AppScreens.RecomFilmSplashScreen.route){
            RecomFilmSplashScreen(navController)
        }

        composable(route = AppScreens.PrivacyPolicyScreen.route){
            PrivacyPolicyScreen(navController)
        }

        composable(route = AppScreens.InfoRecomFilm.route){
            InfoRecomFilm(navController)
        }

        composable(route = AppScreens.NewUserScreen.route){
            NewUserScreen(navController, viewModel = LoginScreenViewModel())
        }

        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController, viewModel = LoginScreenViewModel())
        }

        composable(route = AppScreens.NewPasswordScreen.route){
            NewPasswordScreen(navController)
        }

        composable(route = AppScreens.HomeAppScreen.route){
            HomeAppScreen(navController, drawerState)
        }

        composable(route = AppScreens.InfoFilmScreen.route){
            InfoFilmScreen(navController)
        }

        composable(route = AppScreens.UserDataScreen.route){
            UserDataScreen(navController, drawerState)
        }

        composable(route = AppScreens.DataScreen.route){
            DataScreen(navController, DataViewModel())
        }

        composable(route = AppScreens.UserMyListScreen.route){
            UserMyListScreen(navController, drawerState)
        }

    }
}
