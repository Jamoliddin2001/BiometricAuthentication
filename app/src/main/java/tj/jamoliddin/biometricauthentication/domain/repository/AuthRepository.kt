package tj.jamoliddin.biometricauthentication.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.interfaces.Auth
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val userCollection: CollectionReference,
    private val firebaseAuth: FirebaseAuth,
    private val persistence: PersistenceRepository
): Auth {

    override fun login(email: String, password: String): Flow<UiState<User?>> = flow {
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
                        persistence.savePassword(password)
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

    override fun register(email: String, password: String, user: User): Flow<UiState<String>> = flow {
        try {
            emit(UiState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = firebaseAuth.uid.toString()
            userCollection.document(uid).set(user).await()
            persistence.saveUser(user)
            persistence.savePassword(password)
            emit(UiState.Success("SUCCESS"))
        } catch (e: Exception) {
            emit(UiState.Error<String>(message = e.message ?: "Error"))
        }
    }

    override fun forgotPassword(email: String): Flow<UiState<String>> = flow {
        try {
            emit(UiState.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            persistence.clearAll()
            emit(UiState.Success(data = "Success"))
        } catch (e: Exception){
            emit(UiState.Error(message = e.message?:"Error"))
        }
    }
}