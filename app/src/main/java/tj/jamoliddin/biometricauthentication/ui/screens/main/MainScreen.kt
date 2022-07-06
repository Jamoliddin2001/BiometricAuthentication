package tj.jamoliddin.biometricauthentication.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val user = mainScreenViewModel.user

    Column(
        modifier = Modifier
            .offset(y = ((-48).dp))
            .padding(horizontal = 64.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = user.toString(), color = Primary)
    }

}