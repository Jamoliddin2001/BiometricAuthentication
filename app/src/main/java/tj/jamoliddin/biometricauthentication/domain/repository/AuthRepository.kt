package tj.jamoliddin.biometricauthentication.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.interfaces.AuthRepo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val userCollection: CollectionReference,
    private val firebaseAuth: FirebaseAuth,
    private val persistence: PersistenceRepository
) {

    fun addUser(user: User) {
        try {
            userCollection.document().set(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun login(email: String, password: String): Flow<UiState<User?>> = flow {
        try {
            emit(UiState.Loading)
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            try {
                val uid = firebaseAuth.uid.toString()
                /**Get_User_From_Login_With_UID**/
                try {
                    val data = userCollection.document(uid).get().await()
                    try {
                        val user = data.toObject(User::class.java)
                        persistence.saveUser(user)
                        emit(UiState.Success(user))
                    } catch (e: Exception){
                        emit(UiState.Error(message = e.message ?: "Error"))
                    }
                } catch (e: Exception){
                    emit(UiState.Error(message = e.message ?: "Error"))
                }
            } catch (e: Exception){
                emit(UiState.Error(message = e.message ?: "Error"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(message = e.message ?: "Error"))
        }
    }

    fun register(email: String, password: String): Flow<UiState<String>> = flow {
        try {
            emit(UiState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = firebaseAuth.uid.toString()
            userCollection.document(uid).set(User("+9","J","S","2001")).await()
            persistence.saveUser(User("+9","J","S","2001"))
            emit(UiState.Success("SUCCESS"))
        } catch (e: Exception) {
            emit(UiState.Error<String>(message = e.message ?: "Error"))
        }
    }

    fun saveUserData(user: User) {

    }

    private fun getUserFromLogin(uid: String) {

    }

}