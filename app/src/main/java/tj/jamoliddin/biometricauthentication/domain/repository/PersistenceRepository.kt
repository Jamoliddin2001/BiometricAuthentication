package tj.jamoliddin.biometricauthentication.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.Timestamp
import com.google.gson.Gson
import tj.jamoliddin.biometricauthentication.data.model.Came
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.interfaces.Persistence
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.domain.util.USER
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PersistenceRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Persistence {

    private val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

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

    override fun setCame(date: Came) {
        val data = Gson().toJson(date)
        sharedPreferences.edit {
            putString("date", data)
        }
    }

    override fun isCame(date: Timestamp): Boolean {
        val came = sharedPreferences.getString("date", null)
        try {
            val data = Gson().fromJson(came, Came::class.java)
            val milliseconds1 = data.date.seconds * 1000 + data.date.nanoseconds / 1000000
            val netDate1 = Date(milliseconds1)
            val date1 = sdf.format(netDate1).toString().substringBefore(" ")

            val milliseconds2 = date.seconds * 1000 + date.nanoseconds / 1000000
            val netDate2 = Date(milliseconds2)
            val date2 = sdf.format(netDate2).toString().substringBefore(" ")
            return date1 == date2
        } catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }
}