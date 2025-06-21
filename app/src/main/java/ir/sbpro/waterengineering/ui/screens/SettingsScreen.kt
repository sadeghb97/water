package ir.sbpro.waterengineering.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.ui.components.ScreenWrapper
import ir.sbpro.waterengineering.ui.components.SizeConfigConvert
import ir.sbpro.waterengineering.ui.components.SizeSlider
import ir.sbpro.waterengineering.ui.dialogs.ColorPickerDialog
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val PC_PRIMARY = "PC_PRIMARY"
    val PC_SECONDARY = "PC_SECONDARY"

    val context = LocalContext.current
    val appSingleton = AppSingleton.getInstance()
    val appDataStore = appSingleton.appDataStore
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val pickColor: MutableState<Color?> = remember { mutableStateOf(null) }
    val pcCode: MutableState<String?> = remember { mutableStateOf(null) }
    val lang by appDataStore.languageFlow.collectAsState(appSingleton.startLanguage)
    val appSettings = appDataStore.appSettingsFlow.collectAsState(AppSettings())

    val toggleSetting: (Preferences.Key<Boolean>, Boolean) -> Unit = {prefKey, currentValue ->
        coroutineScope.launch {
            appDataStore.safeTransaction { prefs ->
                prefs[prefKey] = !currentValue
            }
        }
    }

    ScreenWrapper(
        lang = lang,
        navController = navController,
        drawerState = null
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(top = 50.dxp, start = 24.dxp, end = 24.dxp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                lang.settings(),
                fontSize = 24.sxp,
                fontWeight = FontWeight.Bold,
                color = appSettings.value.secondaryColor
            )
            Spacer(modifier = Modifier.height(30.dxp))

            SizeSlider(
                lang = lang,
                label = lang.displaySize(),
                initialValue = SizeConfigConvert.valueToOption(appSettings.value.displaySizeMultiplier)
            ) {
                coroutineScope.launch {
                    val value: Float = SizeConfigConvert.optionToValue(it)
                    appDataStore.safeTransaction { prefs ->
                        prefs[AppDataStore.DISPLAY_SIZE] = value
                    }
                    appDataStore.saveSharedSizeConfig(AppDataStore.SHARED_SETTING_DISPLAY_SIZE, value)
                }
            }
            Spacer(modifier = Modifier.height(30.dxp))

            SizeSlider(
                lang = lang,
                label = lang.fontSize(),
                initialValue = SizeConfigConvert.valueToOption(appSettings.value.fontSizeMultiplier)
            ) {
                coroutineScope.launch {
                    val value: Float = SizeConfigConvert.optionToValue(it)
                    appDataStore.safeTransaction { prefs ->
                        prefs[AppDataStore.FONT_SIZE] = value
                    }
                    appDataStore.saveSharedSizeConfig(AppDataStore.SHARED_SETTING_FONT_SIZE, value)
                }
            }
            Spacer(modifier = Modifier.height(36.dxp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    lang.primaryColor(),
                    fontSize = 21.sxp,
                    fontWeight = FontWeight.Bold,
                    color = appSettings.value.secondaryColor
                )
                Spacer(modifier = Modifier.width(16.dxp))

                SelectColorBox(appSettings.value.primaryColor){
                    pickColor.value = appSettings.value.primaryColor
                    pcCode.value = PC_PRIMARY
                }
            }
            Spacer(modifier = Modifier.height(24.dxp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    lang.secondaryColor(),
                    fontSize = 21.sxp,
                    fontWeight = FontWeight.Bold,
                    color = appSettings.value.secondaryColor
                )
                Spacer(modifier = Modifier.width(16.dxp))

                SelectColorBox(appSettings.value.secondaryColor){
                    pickColor.value = appSettings.value.secondaryColor
                    pcCode.value = PC_SECONDARY
                }
            }
            Spacer(modifier = Modifier.height(24.dxp))

            /*SettingToggle(lang.settingKeepScreenOn(), clockSettings.value.keepScreenOn) {
                toggleSetting(AppDataStore.SETTING_KEEP_SCREEN_ON, clockSettings.value.keepScreenOn)
            }

            SettingToggle(lang.settingVibrateAfterMove(), clockSettings.value.vibrateAfterMove) {
                toggleSetting(AppDataStore.SETTING_VIBRATE_AFTER_MOVE, clockSettings.value.vibrateAfterMove)
            }*/

            //Spacer(modifier = Modifier.height(32.dxp))

            Text(
                lang.language(),
                fontSize = 21.sxp,
                fontWeight = FontWeight.Bold,
                color = appSettings.value.secondaryColor
            )
            Spacer(modifier = Modifier.height(8.dxp))

            val langCallback: (String) -> Unit = { code ->
                coroutineScope.launch {
                    appDataStore.safeTransaction { prefs ->
                        prefs[AppDataStore.LANGUAGE_KEY] = code
                    }
                    appSingleton.appDataStore.saveSharedLanguage(code)
                }
            }

            LanguageOption(appSettings.value, lang, "English", "en") { langCallback("en") }
            LanguageOption(appSettings.value, lang, "فارسی", "fa") { langCallback("fa") }
        }
    }

    ColorPickerDialog(
        confirmText = lang.selectColor(),
        active = pickColor.value != null,
        initColor = pickColor.value,
        onDismiss = {
            pickColor.value = null
            pcCode.value = null
        }
    ) { chosenColor ->
        val dsKey = if(pcCode.value == PC_PRIMARY) AppDataStore.PRIMARY_COLOR else AppDataStore.SECONDARY_COLOR
        val sharedPrefKey = if(pcCode.value == PC_PRIMARY) AppDataStore.SHARED_SETTING_PRIMARY_COLOR
        else AppDataStore.SHARED_SETTING_SECONDARY_COLOR

        coroutineScope.launch {
            appDataStore.safeTransaction { prefs ->
                prefs[dsKey] = chosenColor.toArgb()
            }
        }
        appDataStore.saveSharedColorConfig(sharedPrefKey, chosenColor)

        pickColor.value = null
        pcCode.value = null
    }
}

/*
@Composable
fun SettingToggle(title: String, value: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dxp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = Color.White,
            fontSize = 20.sxp,
            modifier = Modifier.padding(end = 16.dxp).weight(1f)
        )

        Switch(checked = value, onCheckedChange = onValueChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF69DC9A),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color.Gray
            )
        )
    }
}*/

@Composable
fun SelectColorBox(color: Color, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .height(40.dxp)
            .width(100.dxp)
            .background(color)
            .border(color = Color.Black, width = 2.dxp)
            .clickable {
                onClick()
            }
    )
}

@Composable
fun LanguageOption(
    appSettings: AppSettings,
    lang: AppLanguage,
    label: String,
    value: String,
    onSelected: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(value) }
    ) {
        RadioButton(
            selected = lang.getLangCode() == value,
            onClick = { onSelected(value) },
            colors = RadioButtonDefaults.colors(
                selectedColor = appSettings.secondaryColor,
                unselectedColor = appSettings.secondaryColor,
                disabledSelectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dxp))
        Text(label, color = appSettings.secondaryColor)
    }
}