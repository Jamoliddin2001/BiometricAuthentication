package tj.jamoliddin.biometricauthentication.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tj.jamoliddin.biometricauthentication.domain.repository.PersistenceRepository
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val persistence: PersistenceRepository
): ViewModel(){

    val user = persistence.getUser()

}