package tj.jamoliddin.biometricauthentication.domain.interfaces

import kotlinx.coroutines.flow.Flow
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState

interface Auth {

    fun login(email: String, password: String): Flow<UiState<User?>>

    fun register(email: String, password: String, user: User): Flow<UiState<String>>

    fun forgotPassword(email: String): Flow<UiState<String>>

}