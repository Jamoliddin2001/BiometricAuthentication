package tj.jamoliddin.biometricauthentication.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.interfaces.Persistence
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.domain.util.USER
import javax.inject.Inject

class PersistenceRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Persistence {

    override fun isAuthorized() = getUser()?.email != null

    override fun saveUser(user: User?) {
        val jsonUser = Gson().toJson(user)
        sharedPreferences.edit {
            putString(USER, jsonUser)
        }
    }

    override fun getUser(): User? {
        val json = sharedPreferences.getString(USER, null)
        return Gson().fromJson(json, User::class.java)
    }

    override fun savePassword(password: String) {
        sharedPreferences.edit {
            putString(PASSWORD, password)
        }
    }

    val key = sharedPreferences.getString(PASSWORD, null)

    override fun clearAll() {
        sharedPreferences.edit { clear() }
    }

}