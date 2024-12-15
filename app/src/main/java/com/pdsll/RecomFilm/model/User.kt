package com.pdsll.RecomFilm.model

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Se define una data class llamada User que representa un usuario.
// La clase contiene propiedades como id, userId, displayName y email.
// También incluye una función toMap() que convierte el objeto User en un MutableMap<String, String?> para facilitar su almacenamiento en Firestore.

class User(
    val id: String?,
    val userId: String?,
    val displayName: String?,
    val email: String?,
    val movies: MutableList<String>?
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_Name" to this.displayName,
            "email" to this.email,
            "movies" to this.movies
        )
    }
}