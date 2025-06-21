package ir.sbpro.waterengineering.models

import androidx.compose.ui.graphics.Color
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.utils.SizeInterface

data class AppSettings(
    val displaySizeMultiplier: Float = SizeInterface.displaySizeMultiplier.value,
    val fontSizeMultiplier: Float = SizeInterface.fontSizeMultiplier.value,
    val darkColor: Color = AppSingleton.getInstance().appDataStore.getSharedConfigColor(
        AppDataStore.SHARED_SETTING_DARK_COLOR
    ),
    val lightColor: Color = AppSingleton.getInstance().appDataStore.getSharedConfigColor(
        AppDataStore.SHARED_SETTING_LIGHT_COLOR
    )
)