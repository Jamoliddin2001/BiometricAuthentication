package tj.jamoliddin.biometricauthentication.domain

sealed class UiState<out T: Any?> {
    data class Success<T : Any?>(val data: T) : UiState<T>()
    data class Error<T : Any?>(val message: String) : UiState<T>()
    object Loading: UiState<Nothing>()
    object Idle: UiState<Nothing>()
}
