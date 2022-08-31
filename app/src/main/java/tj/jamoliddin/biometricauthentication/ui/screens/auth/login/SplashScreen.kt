package tj.jamoliddin.biometricauthentication.ui.screens.auth.login

import android.content.SharedPreferences
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.components.SplashBackdrop
import tj.jamoliddin.biometricauthentication.ui.screens.auth.AuthViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val isAuthorized = authViewModel.isAuthorized
    val context = LocalContext.current
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true ){
        startAnimation = true
        delay(3500)

        val route =  if(isAuthorized) Screen.LoginPasswordScreen.route
        else Screen.LoginScreen.route

        navController.navigate(route){
            popUpTo(Screen.SplashScreen.route){
                inclusive = true
            }
        }
    }
    val controller = rememberSystemUiController()

    SideEffect {
        controller.setSystemBarsColor(
            color = Color.White
        )
    }

    SplashBackdrop(alphaAnim.value)
}