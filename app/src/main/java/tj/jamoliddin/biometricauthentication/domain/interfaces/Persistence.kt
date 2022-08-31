package tj.jamoliddin.biometricauthentication.domain.interfaces

import com.google.firebase.Timestamp
import tj.jamoliddin.biometricauthentication.data.model.Came
import tj.jamoliddin.biometricauthentication.data.model.User

interface Persistence {

    fun isAuthorized(): Boolean

    fun saveUser(user: User?)

    fun getUser(): User?

    fun savePassword(password: String)

    fun clearAll()

    fun setCame(date: Came)

    fun isCame(date: Timestamp): Boolean
}