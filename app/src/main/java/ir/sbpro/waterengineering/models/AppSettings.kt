package ir.sbpro.waterengineering.models

import ir.sbpro.waterengineering.utils.SizeInterface

data class AppSettings(
    val displaySizeMultiplier: Float = SizeInterface.displaySizeMultiplier.value,
    val fontSizeMultiplier: Float = SizeInterface.fontSizeMultiplier.value
)