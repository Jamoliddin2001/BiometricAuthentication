package tj.jamoliddin.biometricauthentication.data.model

data class User(
    val phone: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val email: String? = null
){
    constructor():this("","","","")
}
