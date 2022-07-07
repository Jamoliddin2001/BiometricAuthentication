package tj.jamoliddin.biometricauthentication.domain.interfaces

import tj.jamoliddin.biometricauthentication.data.model.User

interface Persistence {

    fun isAuthorized(): Boolean

    fun saveUser(user: User?)

    fun getUser(): User?

    fun savePassword(password: String)

    fun clearAll()
}