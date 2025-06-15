package ir.sbpro.waterengineering

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.lang.EnLang
import ir.sbpro.waterengineering.lang.FaLang
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppDataStore (val context: Context) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("prefs")

    val languageFlow: Flow<AppLanguage> = context.dataStore.data.map { preferences ->
        val langCode = preferences[LANGUAGE_KEY] ?: AppSingleton.getInstance().startLanguageCode
        if (langCode == "en") EnLang()
        else FaLang()
    }

    suspend fun safeTransaction(transform: suspend (MutablePreferences) -> Unit){
        context.dataStore.edit(transform)
    }

    suspend fun getSnapshot() : Preferences {
        return context.dataStore.data.first()
    }
}