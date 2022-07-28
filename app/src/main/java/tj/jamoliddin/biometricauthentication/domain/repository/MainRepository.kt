package tj.jamoliddin.biometricauthentication.domain.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import tj.jamoliddin.biometricauthentication.data.model.WorkTime
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.interfaces.Main
import tj.jamoliddin.biometricauthentication.domain.util.WORK_TIME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val userCollection: CollectionReference,
    private val firebaseAuth: FirebaseAuth,
    private val persistence: PersistenceRepository
) : Main {

    override fun addWorkTime(workTime: WorkTime): Flow<UiState<String>> = flow {
        try {
            emit(UiState.Loading)
            val uid = firebaseAuth.uid.toString()
            userCollection.document(uid).collection(WORK_TIME)
                .document(workTime.date.toString()).set(workTime).await()
            emit(UiState.Success("SUCCESS"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "ERROR"))
        }
    }

    override fun getHistory(): Flow<UiState<List<WorkTime>>> = flow {
        try {
            emit(UiState.Loading)
            val uid = firebaseAuth.uid.toString()
            val data = userCollection.document(uid).collection(WORK_TIME)
                .orderBy("start", Query.Direction.DESCENDING)
                .limit(7)
                .get().await()
            try {
                val listWorkTime = data.toObjects(WorkTime::class.java)
                emit(UiState.Success(listWorkTime))
            } catch (e: Exception) {
                emit(UiState.Error(e.message ?: "Error"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Error"))
        }
    }
}