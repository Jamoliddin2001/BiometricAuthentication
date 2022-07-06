package tj.jamoliddin.biometricauthentication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.LoginGraph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.MainGraph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.RegisterGraph


@Composable
internal fun Navigation(
    modifier: Modifier, navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Graph.Login.route,
        modifier = modifier
    ) {
        LoginGraph(navController = navController)
        RegisterGraph(navController = navController)
        MainGraph(navController = navController)
    }

}