package tj.jamoliddin.biometricauthentication.ui.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tj.jamoliddin.biometricauthentication.data.model.WorkTime
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.repository.MainRepository
import tj.jamoliddin.biometricauthentication.domain.repository.PersistenceRepository
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val persistence: PersistenceRepository,
    private val mainRepository: MainRepository
): ViewModel(){

    val user = persistence.getUser()

    private val _stateAddWorkTime: MutableState<UiState<String>> = mutableStateOf(UiState.Idle)
    val stateAddWorkTime: State<UiState<String>> = _stateAddWorkTime

    fun addWorkTime(workTime: WorkTime){
        mainRepository.addWorkTime(workTime).onEach { result ->
            when(result){
                is UiState.Loading -> _stateAddWorkTime.value = UiState.Loading
                is UiState.Success -> _stateAddWorkTime.value = UiState.Success(result.data)
                is UiState.Error -> _stateAddWorkTime.value = UiState.Error(result.message)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private val _stateMyWorkTime: MutableState<UiState<List<WorkTime>>> = mutableStateOf(UiState.Idle)
    val stateMyWorkTime: State<UiState<List<WorkTime>>> = _stateMyWorkTime

    fun getMyWorkTime(){
        mainRepository.getHistory().onEach { result ->
            when(result){
                is UiState.Loading -> _stateMyWorkTime.value = UiState.Loading
                is UiState.Success -> _stateMyWorkTime.value = UiState.Success(result.data)
                is UiState.Error -> _stateMyWorkTime.value = UiState.Error(result.message)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun clearAll(){
        persistence.clearAll()
    }
}