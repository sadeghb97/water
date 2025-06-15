package ir.sbpro.waterengineering.lang

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection

interface AppLanguage {
    fun getLangCode(): String
    fun getLayoutDirection(): LayoutDirection
    fun getDefaultFont(): FontFamily

    fun appName(): String
    fun start(): String
    fun settings(): String
    fun language(): String
    fun menu(): String
    fun version(): String

    fun bronsteinTimeControl(): String
    fun suddenDeathTimeControl(): String
    fun hourglassTimeControl(): String
    fun fischerTimeControl(): String
    fun usDelayTimeControl(): String
    fun overtimeTimeControl(): String

    fun bronsteinDescription(): String
    fun suddenDeathDescription(): String
    fun hourglassDescription(): String
    fun fischerDescription(): String
    fun usDelayDescription(): String
    fun overtimeDescription(): String

    fun baseTime(): String
    fun rewardTime(): String
    fun delayTime(): String
    fun stageMoves(): String
    fun firstStage(): String
    fun secondStage(): String
    fun thirdStage(): String

    fun timeControl(): String
    fun chooseTimeControl(): String
    fun differentSettingsForPlayers(): String
    fun enableThirdStage(): String
    fun bothPlayersSettings(): String
    fun whitePlayerSettings(): String
    fun blackPlayerSettings(): String

    fun settingKeepScreenOn(): String
    fun settingShowMoveNumber(): String
    fun settingShowSide(): String
    fun settingDisplayAccurateTime(): String
    fun settingPlaySoundAfterMove(): String
    fun settingVibrateAfterMove(): String

    fun whiteSide(): String
    fun blackSide(): String
    fun winnerSide(): String
    fun loserSide(): String
    fun move(): String
    fun areYouSureToReset(): String
    fun areYouSureToBack(): String
    fun yes(): String
    fun no(): String

    fun aboutApp(): String
    fun commentIn(): String
    fun moreApps(): String
    fun contactUs(): String
    fun bazarSummaryName() : String
    fun bazarFullName() : String
    fun myketName() : String
    fun aptoideName() : String
    fun marketWord() : String
    fun noPlatformMessage(platform: String) : String

    fun puzzlinhoName() : String
    fun puzzlinhoDescription() : String
    fun cinemaniaName() : String
    fun cinemaniaDescription() : String
    fun snakeName() : String
    fun snakeDescription() : String
    fun tekkenEightName() : String
    fun tekkenEightDescription() : String
    fun sudokuSolverName() : String
    fun sudokuSolverDescription() : String

    fun getParameterTitle(paramKey: String) : String
    fun getFormulaTitle(formulaKey: String) : String
}