package tj.jamoliddin.biometricauthentication.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.ForgotPasswordScreen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.LoginPasswordScreen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.login.LoginScreen


fun NavGraphBuilder.LoginGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = Graph.Login.route
    ) {
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.LoginPasswordScreen.route
        ) {
            LoginPasswordScreen(navController = navController)
        }
        composable(
            route = Screen.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}