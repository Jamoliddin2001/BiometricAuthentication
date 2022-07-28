package tj.jamoliddin.biometricauthentication.data.model

import com.google.firebase.Timestamp

data class WorkTime(
    val date: String? = null,
    val start: Timestamp? = null,
    val end: Timestamp? = null
){
    constructor():this("",null,null)
}
