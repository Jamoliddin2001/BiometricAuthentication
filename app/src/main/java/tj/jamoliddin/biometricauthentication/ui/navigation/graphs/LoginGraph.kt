package tj.jamoliddin.biometricauthentication.ui.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.ForgotPasswordScreen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.LoginPasswordScreen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.LoginScreen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.SplashScreen


@RequiresApi(Build.VERSION_CODES.P)
fun NavGraphBuilder.LoginGraph(
    navController: NavController,
    activity: FragmentActivity
) {
    navigation(
        startDestination = Screen.SplashScreen.route,
        route = Graph.Login.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ){
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.LoginPasswordScreen.route
        ) {
            LoginPasswordScreen(navController = navController, activity = activity)
        }
        composable(
            route = Screen.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}