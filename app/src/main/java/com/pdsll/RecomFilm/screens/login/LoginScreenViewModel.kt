package com.pdsll.RecomFilm.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.pdsll.RecomFilm.model.User
import kotlinx.coroutines.launch

/**
 * @author Pedro David Serrano Llorca - 2º DAM - PMDM
 */

// Esta es la clase ViewModel llamada LoginScreenViewModel que maneja la lógica de la pantalla de inicio de sesión.
// Contiene funciones para iniciar sesión con correo electrónico y contraseña.
// Crear un nuevo usuario con correo electrónico, contraseña y nombre de usuario, y añadirlo a la base de datos Firestore.
// Utiliza Firebase Authentication y Firebase Firestore para la autenticación y el almacenamiento de datos del usuario.
// La función signInWithEmailAndPassword realiza el inicio de sesión con correo electrónico y contraseña. Llama a un callback con un booleano que indica si el inicio de sesión fue exitoso.
// La función createUserWithEmailAndPassword crea un nuevo usuario con correo electrónico, contraseña y nombre de usuario. Llama a un callback cuando se completa el proceso de creación de usuario.
// La función createUser crea un nuevo usuario en la base de datos Firestore utilizando la clase de datos User y la convierte en un mapa antes de almacenarla en Firestore.


class LoginScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password: String, home: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("RecomFilm", "Login correcto")
                        home(true)
                    } else {
                        Log.d("RecomFilm", "Error de login: ${task.exception?.message}")
                        home(false)
                    }
                }
        } catch (ex: Exception) {
            Log.d("RecomFilm", "Error: ${ex.message}")
            home(false)
        }
    }

    // Modificamos esta función para aceptar un array de strings
    fun createUserWithEmailAndPassword(email: String, password: String, displayName: String, movies: List<String>, home: () -> Unit){
        if (_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        createUser(email, displayName, movies) // Pasamos el array de strings
                        home()
                    } else {
                        home()
                    }
                    _loading.value = false
                }
        }
    }

    // Modificamos esta función para aceptar un array de strings
    private fun createUser(email: String, displayName: String, movies: List<String>) {
        val userId = auth.currentUser?.uid

        val user = User(
            userId = userId.toString(),
            displayName = displayName.toString(),
            email = email.toString(),
            id = null,
            movies = null,
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("RecomFilm", "DocumentSnapshot written with ID: ${documentReference.id}")
                // Actualizamos el documento recién creado con el array de strings vacío
                val userRef = FirebaseFirestore.getInstance().collection("users").document(documentReference.id)
                userRef.update("movies", listOf<String>()) // Añadimos el arrayfav vacío
            }
            .addOnFailureListener { e ->
                Log.w("RecomFilm", "Error adding document", e)
            }
    }

}