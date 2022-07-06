package tj.jamoliddin.biometricauthentication.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.screens.main.MainScreen


fun NavGraphBuilder.MainGraph(
    navController: NavController
){
    navigation(
        startDestination = Screen.MainScreen.route,
        route = Graph.Main.route
    ){
        composable(
            route = Screen.MainScreen.route
        ){
            MainScreen(navController = navController)
        }
    }
}