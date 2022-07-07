package tj.jamoliddin.biometricauthentication.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.screens.auth.registration.RegistrationScreen


fun NavGraphBuilder.RegisterGraph(
    navController: NavController
){
    navigation(
        startDestination = Screen.RegisterScreen.route,
        route = Graph.Register.route
    ){
        composable(
            route = Screen.RegisterScreen.route
        ){
            RegistrationScreen(navController = navController)
        }
    }
}