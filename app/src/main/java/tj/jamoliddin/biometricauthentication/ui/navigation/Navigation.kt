package tj.jamoliddin.biometricauthentication.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import tj.jamoliddin.biometricauthentication.domain.model.Graph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.LoginGraph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.MainGraph
import tj.jamoliddin.biometricauthentication.ui.navigation.graphs.RegisterGraph


@RequiresApi(Build.VERSION_CODES.P)
@Composable
internal fun Navigation(
    modifier: Modifier, navController: NavHostController, activity: FragmentActivity
) {

    NavHost(
        navController = navController,
        startDestination = Graph.Login.route,
        modifier = modifier
    ) {
        LoginGraph(navController = navController, activity = activity)
        RegisterGraph(navController = navController)
        MainGraph(navController = navController)
    }

}