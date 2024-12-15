package com.pdsll.RecomFilm.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdsll.RecomFilm.R
import com.pdsll.RecomFilm.model.DataViewModel
import com.pdsll.RecomFilm.navigation.AppScreens
import com.pdsll.RecomFilm.screens.HomeAppScreens
import kotlinx.coroutines.launch

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuLateral(
    navController: NavController,
    drawerState: DrawerState,
    dataViewModel: DataViewModel
) {
    val scope = rememberCoroutineScope()

    val drawerItem = listOf(
        DrawerItems(Icons.Outlined.Face, "Perfil", AppScreens.UserDataScreen.route, 0, false),
        DrawerItems(Icons.Outlined.Search, "Buscar pelicula", AppScreens.DataScreen.route, 0, false),
        DrawerItems(Icons.Outlined.Call, "Contacto", AppScreens.InfoRecomFilm.route, 0, false),
        DrawerItems(Icons.Outlined.Info, "Politica de Privacidad", AppScreens.PrivacyPolicyScreen.route, 0, false),
        DrawerItems(Icons.Outlined.ExitToApp, "Cerrar sesión", AppScreens.LoginScreen.route, 0, false)
    )

    var selectedItem by remember { mutableStateOf(drawerItem[0]) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet (Modifier.width(300.dp)){

                Column(
                    Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                        .background(Color(0xFF6A06BA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.imagen_menu_despegable),
                                contentDescription = "Imagen de menu lateral",
                                modifier = Modifier
                                    .size(600.dp)
                            )

                            Text(
                                text = "RecomFilm",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Divider(Modifier.align(Alignment.BottomCenter), thickness = 1.dp, Color.DarkGray)
                    }

                    drawerItem.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.title) },
                            selected = item == selectedItem,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(item.route)
                            },
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .padding(horizontal = 16.dp),
                            icon = { Icon(imageVector = item.icon, tint = Color(0xFF6A06BA), contentDescription = item.title) },
                            badge = {
                                if (item.hasBadge) {
                                    BadgedBox(badge = {
                                        Badge {
                                            Text(text = item.badgeCount.toString(), fontSize = 17.sp)
                                        }
                                    }) {

                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        // Llamamos a BodyHomeApp para visualizar su contenido
        HomeAppScreens(navController,drawerState)
    }
}

/**
 * Clase de datos que representa un elemento del menú lateral.
 * Cada elemento contiene un icono, un título, una ruta de navegación, un contador de distintivos y un indicador de si tiene un distintivo.
 */
data class DrawerItems(
    val icon: ImageVector,
    val title: String,
    val route: String,
    val badgeCount: Int,
    val hasBadge: Boolean
)