package com.pdsll.RecomFilm.navigation

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Se define un conjunto sellado (sealed class) llamado AppScreens que representa las pantallas de la aplicación y sus respectivas rutas.
// Cada pantalla se define como un objeto dentro de la clase sellada y se inicializa con su ruta correspondiente.
// Las pantallas incluidas son: RecomFilmSplashScreen, LoginScreen, NewUserScreen, NewPasswordScreen, HomeAppScreen y PrivacyPolicyScreen.
// Además, se añade la pantalla DataScreen para representar una pantalla adicional en la aplicación.


sealed class AppScreens(val route: String) {
    object RecomFilmSplashScreen: AppScreens("RecomFilmSplashScreen")
    object PrivacyPolicyScreen: AppScreens("PrivacyPolicyScreen")
    object InfoRecomFilm: AppScreens("InfoRecomFilm")
    object NewUserScreen: AppScreens("NewUserScreen")
    object LoginScreen: AppScreens("LoginScreen")
    object NewPasswordScreen: AppScreens("NewPasswordScreen")
    object HomeAppScreen: AppScreens("HomeAppScreen")
    object InfoFilmScreen: AppScreens("InfoFilmScreen")
    object UserDataScreen: AppScreens("UserDataScreen")
    object DataScreen: AppScreens("DataScreen")
    object UserMyListScreen: AppScreens("UserMyListScreen")
}
