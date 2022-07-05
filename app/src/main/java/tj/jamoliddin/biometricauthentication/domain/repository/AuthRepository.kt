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
    private val firebaseAuth: FirebaseAuth
) {

    fun addUser(user: User) {
        try {
            userCollection.document().set(user)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun login(email: String, password: String) {

    }

    fun register(email: String, password: String): Flow<UiState<String>> = flow {
        try {
            emit(UiState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(UiState.Success("SUCCESS"))
        } catch (e: Exception){
            emit(UiState.Error<String>(message = e.message?:"Error"))
        }
    }

    fun saveUserData(user: User) {

    }

    fun getUserFromLogin(email: String, password: String) {

    }

}