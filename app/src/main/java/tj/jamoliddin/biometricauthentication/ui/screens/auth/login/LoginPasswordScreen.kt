package tj.jamoliddin.biometricauthentication.ui.screens.auth.login

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun LoginPasswordScreen(
    navController: NavController
) {
    val context = LocalContext.current

    Text(text = "LoginPasswordScreen")
}