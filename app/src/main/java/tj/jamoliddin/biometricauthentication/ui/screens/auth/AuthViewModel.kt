package tj.jamoliddin.biometricauthentication.ui.screens.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.repository.AuthRepository
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _state: MutableState<UiState<String>> = mutableStateOf(UiState.Idle)
    val state: State<UiState<String>> = _state

    fun register(email: String, password: String) {
        authRepository.register(email,password).onEach { result ->
            when(result){
                is UiState.Loading -> _state.value = UiState.Loading
                is UiState.Success -> _state.value = UiState.Success(result.data)
                is UiState.Error -> _state.value = UiState.Error(result.message)
                else -> {

                }
            }
        }.launchIn(viewModelScope)
    }

}