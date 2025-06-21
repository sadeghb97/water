package ir.sbpro.waterengineering

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ir.sbpro.waterengineering.formulas.FormulaResult
import ir.sbpro.waterengineering.formulas.ParametersState
import ir.sbpro.waterengineering.formulas.WaterEngFormula
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.lang.EnLang
import ir.sbpro.waterengineering.lang.FaLang
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.utils.getCurrentSystemLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppDataStore (val context: Context) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val DISPLAY_SIZE = floatPreferencesKey("display_size")
        val FONT_SIZE = floatPreferencesKey("font_size")
        val DARK_COLOR = intPreferencesKey("dark_color")
        val LIGHT_COLOR = intPreferencesKey("light_color")
        val SHARED_SETTINGS_TOKEN = "shared_settings"
        val SHARED_SETTING_LANGUAGE = "shared_setting_language"
        val SHARED_SETTING_DISPLAY_SIZE = "shared_setting_display_size"
        val SHARED_SETTING_FONT_SIZE= "shared_setting_font_size"
        val SHARED_SETTING_DARK_COLOR = "shared_setting_dark_color"
        val SHARED_SETTING_LIGHT_COLOR= "shared_setting_light_color"
    }

    private val PARAM_PREFIX_KEY = "param_"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("prefs")
    val sharedPrefs = context.getSharedPreferences(SHARED_SETTINGS_TOKEN, Context.MODE_PRIVATE)

    val languageFlow: Flow<AppLanguage> = context.dataStore.data.map { preferences ->
        val langCode = preferences[LANGUAGE_KEY] ?: getSharedLanguage()
        if (langCode == "en") EnLang()
        else FaLang()
    }

    val appSettingsFlow: Flow<AppSettings> = context.dataStore.data.map { preferences ->
        val dm: Float = preferences[DISPLAY_SIZE] ?: getSharedSizeConfig(SHARED_SETTING_DISPLAY_SIZE)
        val fm: Float = preferences[FONT_SIZE] ?: getSharedSizeConfig(SHARED_SETTING_FONT_SIZE)
        val darkColorCode = preferences[DARK_COLOR]
        val lightColorCode = preferences[LIGHT_COLOR]

        val darkColor = if(darkColorCode != null) Color(darkColorCode)
        else getSharedConfigColor(SHARED_SETTING_DARK_COLOR)

        val lightColor = if(lightColorCode != null) Color(lightColorCode)
        else getSharedConfigColor(SHARED_SETTING_LIGHT_COLOR)

        AppSettings(
            displaySizeMultiplier = dm,
            fontSizeMultiplier = fm,
            darkColor = darkColor,
            lightColor = lightColor
        )
    }

    suspend fun safeTransaction(transform: suspend (MutablePreferences) -> Unit){
        context.dataStore.edit(transform)
    }

    suspend fun getSnapshot() : Preferences {
        return context.dataStore.data.first()
    }

    fun saveSharedLanguage(language: String) {
        sharedPrefs.edit() { putString(SHARED_SETTING_LANGUAGE, language) }
    }

    fun saveSharedSizeConfig(config: String, value: Float) {
        sharedPrefs.edit() { putFloat(config, value) }
    }

    fun saveSharedColorConfig(config: String, color: Color) {
        sharedPrefs.edit() { putInt(config, color.toArgb()) }
    }

    fun getSharedLanguage(): String {
        val systemLanguage = getCurrentSystemLanguage()
        return sharedPrefs.getString(SHARED_SETTING_LANGUAGE, systemLanguage) ?: systemLanguage
    }

    fun getSharedSizeConfig(config: String) : Float {
        return sharedPrefs.getFloat(config, 1f)
    }

    fun getSharedConfigColor(config: String) : Color {
        val colorCode = sharedPrefs.getInt(config, 0)
        return if(colorCode > 0) Color(colorCode)
        else if(config == SHARED_SETTING_DARK_COLOR) Color.Black
        else Color.White
    }

    fun saveFormulaResult(formula: WaterEngFormula, parametersState: ParametersState, formulaResults: List<FormulaResult>){
        sharedPrefs.edit() {
            parametersState.params.forEachIndexed { index, item ->
                putFloat(PARAM_PREFIX_KEY + formula.parameters[index], item.value.text.toFloat())
            }
            formulaResults.forEach { result ->
                putFloat(PARAM_PREFIX_KEY + result.key, result.value ?: 1f)
            }
        }
    }

    fun getParamSavedValue(paramKey: String) : Float {
        return sharedPrefs.getFloat(PARAM_PREFIX_KEY +  paramKey, 1f)
    }
}