package tj.jamoliddin.biometricauthentication.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import tj.jamoliddin.biometricauthentication.ui.navigation.Navigation
import tj.jamoliddin.biometricauthentication.ui.theme.GrayIndicator


@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberSystemUiController()
            SideEffect {
                controller.setSystemBarsColor(
                    color = GrayIndicator
                )
            }
            val navController = rememberNavController()
            Navigation(
                modifier = Modifier,
                navController = navController,
                activity = this
            )
        }
    }

}