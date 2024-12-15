package com.pdsll.RecomFilm.model

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Se define la clase DataViewModel que actúa como ViewModel en el patrón de arquitectura MVVM.
// Esta clase maneja la lógica de negocio y la interacción con Firebase Firestore para obtener y buscar datos relacionados con películas.

class DataViewModel : ViewModel() {
    val state = mutableStateOf<List<Items>>(emptyList())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            state.value = getAllDataFromFirestore()
        }
    }

    suspend fun getAllDataFromFirestore(): List<Items> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Items>()

        try {
            val documentSnapshots = db
                .collection("films")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .await()
            documentSnapshots.forEach { documentSnapshot ->
                val result = documentSnapshot.toObject<Items>()
                itemList.add(result)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("Error", "getAllDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

    fun searchData(searchTerm: String, option: String) {
        viewModelScope.launch {
            state.value = getDataFromFirestore(searchTerm, option)
        }
    }

    private suspend fun getDataFromFirestore(searchTerm: String, option: String): List<Items> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Items>()

        try {
            val documentSnapshots = when (option) {
                "name" -> {
                    val searchTermLowerCase = searchTerm.toLowerCase()
                    db.collection("films").get().await().filter { documentSnapshot ->
                        // Utilizamos contains con ignoreCase para la búsqueda insensible a mayúsculas y minúsculas
                        val result = documentSnapshot.toObject<Items>()
                        result.name.contains(searchTermLowerCase, ignoreCase = true)
                    }
                }

                "genre" -> {
                    // Mantenemos la búsqueda sensible a mayúsculas y minúsculas para el género
                    db.collection("films").whereEqualTo("genre", searchTerm).get().await()
                }

                else -> throw IllegalArgumentException("Invalid option: $option")
            }

            documentSnapshots.forEach { documentSnapshot ->
                val result = documentSnapshot.toObject<Items>()
                itemList.add(result)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("Error", "getDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

    private suspend fun getDataFromFirestore(searchTerm: String): List<Items> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Items>()

        try {
            val documentSnapshots = db.collection("films")
                .whereArrayContains("searchKeywords", searchTerm.toLowerCase())
                .get()
                .await()

            documentSnapshots.forEach { documentSnapshot ->
                val result = documentSnapshot.toObject<Items>()
                itemList.add(result)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("Error", "getDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

    fun searchUser(searchTerm: String) {
        viewModelScope.launch {
            state.value = getDatasearchUser(searchTerm)
        }
    }

    private suspend fun getDatasearchUser(searchTerm: String): List<Items> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Items>()
        try {
            val searchTermLowerCase = searchTerm.toLowerCase()
            val querySnapshot = db.collection("users")
                .whereEqualTo("email", searchTermLowerCase) // Buscar por correo electrónico
                .get()
                .await()

            for (documentSnapshot in querySnapshot) {
                val result = documentSnapshot.toObject<User>()
                val item = Items(result.displayName ?: "") // Obtener el displayName si lo necesitas
                itemList.add(item)
            }
        } catch (e: Exception) {
            Log.e("Error", "getDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

    fun getDatasearchUserdsed(searchTerm: String, option: String) {
        viewModelScope.launch {
            state.value = getDatasearchUserds(searchTerm)
        }
    }

    suspend fun getDatasearchUserds(searchTerm: String): List<Items> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Items>()
        try {
            val searchTermLowerCase = searchTerm.toLowerCase()
            val querySnapshot = db.collection("users")
                .get()
                .await()

            for (documentSnapshot in querySnapshot) {
                val result = documentSnapshot.toObject<User>()
                if (result.displayName?.contains(searchTermLowerCase, ignoreCase = true) == true) {
                    // Si el nombre coincide con el término de búsqueda, agregarlo a itemList
                    val item = Items(result.displayName ?: "")
                    itemList.add(item)
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "getDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

suspend fun getFilmFavoritesByEmailFromFirestores(email: String): List<String> {
    val db = FirebaseFirestore.getInstance()
    val filmFavoritesList = mutableListOf<String>()

    try {
        // Buscar el usuario por su correo electrónico para obtener su ID de usuario
        val querySnapshot = db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val userId = querySnapshot.documents[0].id

            // Utilizar el ID de usuario para obtener las películas favoritas
            val documentSnapshot = db.collection("users")
                .document(userId)
                .get()
                .await()

            val filmfav = documentSnapshot.getString("filmfav")
            filmfav?.let {
                filmFavoritesList.add(it)
            }
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("Error", "getFilmFavoritesByEmailFromFirestores: $e")
        e.printStackTrace()
    }

    return filmFavoritesList
}

    suspend fun getDisplayNameByEmailFromFirestore(email: String): String? {
        val db = FirebaseFirestore.getInstance()
        var displayName: String? = null

        try {
            // Buscar el usuario por su correo electrónico para obtener su nombre de visualización
            val querySnapshot = db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                displayName = documentSnapshot.getString("display_Name")
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("Error", "getDisplayNameByEmailFromFirestore: $e")
            e.printStackTrace()
        }

        return displayName
    }

    fun addMovieToUser(userId: String, movieId: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val user = document.toObject(User::class.java)
                user?.movies?.add(movieId)

                userRef.set(user!!.toMap())
                    .addOnSuccessListener {
                        // La película se agregó exitosamente a la lista de películas del usuario en Firebase
                    }
                    .addOnFailureListener { e ->
                        // Manejar el error
                    }
            } else {
                // Manejar el caso en el que no se encuentra el usuario en la base de datos
            }
        }.addOnFailureListener { e ->
            // Manejar el error
        }
    }

    // Esta función buscaría el ID de una película dado su nombre en la base de datos de películas
    fun getMovieIdByName(movieName: String, onSuccess: (String?) -> Unit) {
        // Aquí deberías realizar la lógica para buscar el ID de la película en tu base de datos de películas
        // Supongamos que tienes una colección "movies" en Firestore que contiene documentos con campos "name" y "id"

        val db = FirebaseFirestore.getInstance()
        val moviesCollection = db.collection("movies")

        // Realizamos una consulta para obtener el documento de la película por su nombre
        val query = moviesCollection.whereEqualTo("name", movieName)

        query.get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                // Si se encuentra un documento con el nombre de la película, retornamos su ID
                onSuccess(documents.documents[0].id)
            } else {
                // Si no se encuentra la película, retornamos null
                onSuccess(null)
            }
        }.addOnFailureListener { e ->
            // Manejar el error
            onSuccess(null)
        }
    }

}
