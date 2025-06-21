package ir.sbpro.waterengineering.models

import androidx.compose.ui.graphics.Color
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.utils.SizeInterface

data class AppSettings(
    val displaySizeMultiplier: Float = SizeInterface.displaySizeMultiplier.value,
    val fontSizeMultiplier: Float = SizeInterface.fontSizeMultiplier.value,
    val primaryColor: Color = AppSingleton.getInstance().appDataStore.getSharedConfigColor(
        AppDataStore.SHARED_SETTING_PRIMARY_COLOR
    ),
    val secondaryColor: Color = AppSingleton.getInstance().appDataStore.getSharedConfigColor(
        AppDataStore.SHARED_SETTING_SECONDARY_COLOR
    )
)