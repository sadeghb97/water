package ir.sbpro.waterengineering

import android.content.Context
import android.content.pm.PackageManager
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.lang.EnLang
import ir.sbpro.waterengineering.lang.FaLang

class AppSingleton {
    companion object {
        private var _instance: AppSingleton? = null

        fun getInstance(context: Context? = null) : AppSingleton {
            if(_instance != null) return _instance!!
            if(context == null) throw Exception("Null context for AppSingleton instance!")

            _instance = AppSingleton()
            _instance!!.appDataStore = AppDataStore(context)
            return _instance!!
        }
    }

    fun loadDetails(context: Context){
        startLanguageCode = appDataStore.getSavedSharedLanguage().lowercase()
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

    lateinit var appDataStore: AppDataStore
    lateinit var startLanguageCode: String
    lateinit var startLanguage: AppLanguage
    lateinit var verName: String
    var verCode: Int = 0
}