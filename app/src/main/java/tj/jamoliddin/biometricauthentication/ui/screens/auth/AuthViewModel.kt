package tj.jamoliddin.biometricauthentication.ui.screens.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.repository.AuthRepository
import tj.jamoliddin.biometricauthentication.domain.repository.PersistenceRepository
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val persistence: PersistenceRepository
): ViewModel(){

    val isAuthorized = persistence.isAuthorized()

    private val _state: MutableState<UiState<String>> = mutableStateOf(UiState.Idle)
    val state: State<UiState<String>> = _state

    fun register(email: String, password: String, user: User) {
        authRepository.register(email,password, user).onEach { result ->
            when(result){
                is UiState.Loading -> _state.value = UiState.Loading
                is UiState.Success -> _state.value = UiState.Success(result.data)
                is UiState.Error -> _state.value = UiState.Error(result.message)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private val _stateLogin: MutableState<UiState<User?>> = mutableStateOf(UiState.Idle)
    val stateLogin: State<UiState<User?>> = _stateLogin

    fun login(email: String, password: String) {
        authRepository.login(email, password).onEach { result ->
            when(result){
                is UiState.Loading -> _stateLogin.value = UiState.Loading
                is UiState.Success -> _stateLogin.value = UiState.Success(result.data)
                is UiState.Error -> _stateLogin.value = UiState.Error(result.message)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    val password = persistence.key

    private val _stateForgotPassword: MutableState<UiState<String>> = mutableStateOf(UiState.Idle)
    val stateForgotPassword: State<UiState<String>> = _stateForgotPassword

    fun forgotPassword(email: String){
        authRepository.forgotPassword(email).onEach { result ->
            when(result){
                is UiState.Loading -> _stateForgotPassword.value = UiState.Loading
                is UiState.Success -> _stateForgotPassword.value = UiState.Success(result.data)
                is UiState.Error -> _stateForgotPassword.value = UiState.Error(result.message)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}