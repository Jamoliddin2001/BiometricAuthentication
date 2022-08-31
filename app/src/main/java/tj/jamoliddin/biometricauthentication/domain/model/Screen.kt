package tj.jamoliddin.biometricauthentication.domain.model

sealed class Screen(
    val route: String
) {
    object LoginScreen: Screen("LoginScreen")
    object LoginPasswordScreen: Screen("LoginPasswordScreen")
    object ForgotPasswordScreen: Screen("ForgotPasswordScreen")
    object RegisterScreen: Screen("RegisterScreen")
    object MainScreen: Screen("MainScreen")
    object SplashScreen: Screen("SplashScreen")
}