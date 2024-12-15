import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pdsll.RecomFilm.model.Items
import kotlinx.coroutines.tasks.await

class MovieViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getAllMovies(): List<Items> {
        val movies = mutableListOf<Items>()
        try {
            val querySnapshot = db.collection("peliculas").get().await()
            for (document in querySnapshot.documents) {
                val movie = document.toObject(Items::class.java)
                movie?.let {
                    movies.add(it)
                }
            }
        } catch (e: Exception) {
            // Manejar errores de recuperación de datos
        }
        return movies
    }

    suspend fun getMovieById(id: String): Items? {
        try {
            val documentSnapshot = db.collection("peliculas").document(id).get().await()
            return documentSnapshot.toObject(Items::class.java)
        } catch (e: Exception) {
            // Manejar errores de recuperación de datos
        }
        return null
    }
}