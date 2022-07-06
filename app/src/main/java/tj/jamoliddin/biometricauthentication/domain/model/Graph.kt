package tj.jamoliddin.biometricauthentication.domain.model


sealed class Graph(
    val route: String
) {

    object Login: Graph("LoginGraph")
    object Register: Graph("RegisterGraph")
    object Main: Graph("MainGraph")

}