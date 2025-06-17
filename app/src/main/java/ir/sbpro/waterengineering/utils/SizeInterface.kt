package ir.sbpro.waterengineering.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import kotlinx.coroutines.launch
import kotlin.math.ceil

object SizeInterface {
    val appSingleton: AppSingleton = AppSingleton.getInstance()
    var sh: Int = 0
    var sw: Int = 0
    var shr: Double = 1.0
    var swr: Double = 1.0
    var minsm: Double = 1.0
    var userFontScale = 1f

    val displaySizeMultiplier: MutableState<Float> = mutableStateOf(
        appSingleton.appDataStore.getSharedSizeConfig(AppDataStore.SHARED_SETTING_DISPLAY_SIZE)
    )
    val fontSizeMultiplier: MutableState<Float> = mutableStateOf(
        appSingleton.appDataStore.getSharedSizeConfig(AppDataStore.SHARED_SETTING_FONT_SIZE)
    )

    @Composable
    fun Init(){
        userFontScale = LocalDensity.current.fontScale

        val configuration = LocalConfiguration.current
        sh = configuration.screenHeightDp
        sw = configuration.screenWidthDp
        println("XQQQSize: ($sh - $sw - $userFontScale)")

        shr = sh.toDouble() / 795
        swr = sw.toDouble() / 393
        minsm = if(shr < swr) shr else swr

        displaySizeMultiplier.value = appSingleton.appDataStore.getSharedSizeConfig(AppDataStore.SHARED_SETTING_DISPLAY_SIZE)
        fontSizeMultiplier.value = appSingleton.appDataStore.getSharedSizeConfig(AppDataStore.SHARED_SETTING_FONT_SIZE)
        LaunchedEffect(Unit) {
            launch {
                appSingleton.appDataStore.appSettingsFlow.collect {
                    displaySizeMultiplier.value = it.displaySizeMultiplier
                    fontSizeMultiplier.value = it.fontSizeMultiplier
                }
            }
        }
    }
}

val Int.dxp
    get() = ceil(this.toDouble() * SizeInterface.minsm * SizeInterface.displaySizeMultiplier.value).toInt().dp

val Int.sxp
    get() = ceil(
        this.toDouble() * SizeInterface.minsm * SizeInterface.fontSizeMultiplier.value / SizeInterface.userFontScale
    ).toInt().sp