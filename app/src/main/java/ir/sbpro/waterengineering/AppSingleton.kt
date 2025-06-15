package ir.sbpro.waterengineering

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.edit
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.lang.EnLang
import ir.sbpro.waterengineering.lang.FaLang
import ir.sbpro.waterengineering.utils.getCurrentSystemLanguage

class AppSingleton {
    companion object {
        private var _instance: AppSingleton? = null
        private val SETTINGS_TOKEN = "settings"

        fun getInstance(context: Context? = null) : AppSingleton {
            if(_instance != null) return _instance!!
            if(context == null) throw Exception("Null context for AppSingleton instance!")

            _instance = AppSingleton()
            _instance!!.appDataStore = AppDataStore(context)
            return _instance!!
        }
    }

    fun loadDetails(context: Context){
        startLanguageCode = getSavedLanguage(context).lowercase()
        if(startLanguageCode != "fa" && startLanguageCode != "en"){
            startLanguageCode = "fa"
        }
        startLanguage = if(startLanguageCode == "en") EnLang()
        else FaLang()

        val info = context.packageManager.getPackageInfo(
            context.packageName, PackageManager.GET_ACTIVITIES)
        verName = info.versionName ?: ""
        verCode = info.versionCode
    }

    fun saveLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences(SETTINGS_TOKEN, Context.MODE_PRIVATE)
        prefs.edit() { putString("language", language) }
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(SETTINGS_TOKEN, Context.MODE_PRIVATE)
        val systemLanguage = getCurrentSystemLanguage()
        return prefs.getString("language", systemLanguage) ?: systemLanguage
    }

    lateinit var appDataStore: AppDataStore
    lateinit var startLanguageCode: String
    lateinit var startLanguage: AppLanguage
    lateinit var verName: String
    var verCode: Int = 0
}