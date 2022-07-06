package tj.jamoliddin.biometricauthentication.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.util.USER
import javax.inject.Inject

class PersistenceRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun isAuthorized() = getUser()?.email != null

    fun saveUser(user: User?) {
        val jsonUser = Gson().toJson(user)
        sharedPreferences.edit {
            putString(USER, jsonUser)
        }
    }

    fun getUser(): User? {
        val json = sharedPreferences.getString(USER, null)
        return Gson().fromJson(json, User::class.java)
    }

    fun clearAll() {
        sharedPreferences.edit { clear() }
    }

}