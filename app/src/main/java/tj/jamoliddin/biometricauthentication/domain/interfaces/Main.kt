package tj.jamoliddin.biometricauthentication.domain.interfaces

import kotlinx.coroutines.flow.Flow
import tj.jamoliddin.biometricauthentication.data.model.WorkTime
import tj.jamoliddin.biometricauthentication.domain.UiState

interface Main {

    fun addWorkTime(workTime: WorkTime): Flow<UiState<String>>

    fun getHistory(): Flow<UiState<List<WorkTime>>>

}