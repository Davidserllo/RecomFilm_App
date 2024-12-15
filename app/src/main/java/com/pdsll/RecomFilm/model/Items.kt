package com.pdsll.RecomFilm.model

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Se define una clase llamada Items que representa un elemento de datos relacionado con películas.
// La clase incluye propiedades como nombre, imagen, fecha, director y género de la película.
// Cada propiedad se inicializa con una cadena vacía por defecto.

class Items(
//    val id: Int,
    val image: String = "",
    val name: String = "",
    val date: String = "",
    val director: String = "",
    val genre: String = "",
    val duration: String = "",
    val synopsis: String = "",
)