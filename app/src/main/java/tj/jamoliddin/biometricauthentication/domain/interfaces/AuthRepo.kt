package tj.jamoliddin.biometricauthentication.domain.interfaces

import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState

interface AuthRepo {

    fun login(email: String, password: String): UiState<User>

    fun register(email: String, password: String): UiState<String>

    fun saveUserData(user: User): UiState<String>

    fun getUserFromLogin(email: String, password: String): UiState<User>
}