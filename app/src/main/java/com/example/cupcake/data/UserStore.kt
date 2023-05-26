import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataRepository(private val context: Context) {
    private val FAVORITETEAMS = "FAVORITE"

    fun saveStringList(stringList: List<String>) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val jsonString = Gson().toJson(stringList)
        editor.putString(FAVORITETEAMS, jsonString)
        editor.apply()
    }

    fun getStringList(): List<String> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = sharedPreferences.getString(FAVORITETEAMS, null)
        return if (jsonString != null) {
            Gson().fromJson(jsonString, object : TypeToken<List<String>>() {}.type)
        } else {
            emptyList()
        }
    }
}
