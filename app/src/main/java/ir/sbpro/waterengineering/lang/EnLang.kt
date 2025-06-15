package ir.sbpro.waterengineering.lang

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection

class EnLang : AppLanguage {
    override fun getLangCode() = "en"
    override fun getLayoutDirection() = LayoutDirection.Ltr
    override fun getDefaultFont(): FontFamily { return FontFamily.SansSerif }

    override fun appName() = "Water Engineering Formulas"
    override fun start() = "Start"
    override fun settings() = "Settings"
    override fun language() = "Language"
    override fun menu() = "Menu"
    override fun version() = "Version"

    override fun bronsteinTimeControl() = "Bronstein Delay"
    override fun suddenDeathTimeControl() = "Sudden Death"
    override fun hourglassTimeControl() = "Hourglass"
    override fun fischerTimeControl() = "Fischer"
    override fun usDelayTimeControl() = "US Delay"
    override fun overtimeTimeControl() = "Overtime Format"

    override fun bronsteinDescription() = "this timing method adds time but, unlike Increment, the maximum amount of time is not always added. If a player expends more than the specified delay then the entire delay is added to the player's clock, but if a player moves faster than the delay, only the exact amount of time expended by the player is added."
    override fun suddenDeathDescription() = "This is the simplest methodology. Each player is assigned a fixed amount of time for the whole game. If a player's main time expires, they generally lose the game."
    override fun hourglassDescription() = "Each player's clock starts with a specified time. While one player is deciding a move, their clock time decreases and their opponent's clock time increases. This is analogous to an hourglass: sand empties from one container and fills the other. The sum of both clocks always remains the same, and slow moves give extra time to the opponent. There is no maximum amount of time allotted for a game with this timing method."
    override fun fischerDescription() = "Also known as Increment and Bonus. a specified amount of time is added to the player's main time each move, unless the player's main time ran out before they completed their move. For example, if the time control is 90+30, each player gets an additional thirty seconds added to their main time for each move"
    override fun usDelayDescription() = "Also known as Simple Delay, this timing method causes the clock to wait for the delay period at the start of each move before the player's main time begins to count down. For example, if the delay is ten seconds, the clock waits ten seconds at the beginning of each move before reducing the main time.\n" +
            "Mathematically, Bronstein delay and Simple delay are identical; however, they display time differently."
    override fun overtimeDescription() = "This method divides the game into phases. Initially, each player gets a main time. After it expires, the player enters overtime, where they must make a set number of moves within repeating time periods. Failing to complete the required moves in time results in a loss."

    override fun baseTime() = "Base Time"
    override fun rewardTime() = "Reward Time"
    override fun delayTime() = "Delay Time"
    override fun stageMoves() = "Moves"
    override fun firstStage() = "First Stage"
    override fun secondStage() = "Second Stage"
    override fun thirdStage() = "Third Stage"

    override fun timeControl() = "Time Control"
    override fun chooseTimeControl() = "Select Time Control"
    override fun differentSettingsForPlayers() = "Different settings for each player"
    override fun enableThirdStage() = "Enable third stage"
    override fun bothPlayersSettings() = "Settings"
    override fun whitePlayerSettings() = "White player settings"
    override fun blackPlayerSettings() = "Black player settings"

    override fun settingKeepScreenOn() = "Keep screen on while timer is running"
    override fun settingShowMoveNumber() = "Display move number"
    override fun settingShowSide() = "Display players side"
    override fun settingDisplayAccurateTime() = "Display time with hundredths of a second accuracy"
    override fun settingPlaySoundAfterMove() = "Play sound after move"
    override fun settingVibrateAfterMove() = "Vibrate after move"

    override fun whiteSide() = "White Player"
    override fun blackSide() = "Black Player"
    override fun winnerSide() = "Winner"
    override fun loserSide() = "Loser"
    override fun move(): String = "Move"
    override fun areYouSureToReset() = "Are you sure to reset clock?"
    override fun areYouSureToBack() = "The clock will close. are you sure?"
    override fun yes() = "Yes"
    override fun no()= "No"

    override fun aboutApp()= "About App"
    override fun commentIn(): String = "Comment in"
    override fun moreApps(): String = "More Apps"
    override fun contactUs(): String = "Contact Us"
    override fun bazarSummaryName() : String = "Bazaar"
    override fun bazarFullName() : String = "CafeBazaar"
    override fun myketName() : String = "Myket"
    override fun aptoideName() : String = "Aptoide"
    override fun marketWord(): String = "Market"

    override fun noPlatformMessage(platform: String): String {
        return "Please install latest version of $platform and login to your account"
    }

    override fun puzzlinhoName() : String = "Puzzlinho"
    override fun puzzlinhoDescription() : String = "Football puzzles, Guess footballers and Football Clubs. duel and competition with friends offline"
    override fun cinemaniaName() : String = "Cinemania"
    override fun cinemaniaDescription() : String = "Guessing best movies in history, including more than 1000 prominent movies of the world of cinema for fans of the seventh art"
    override fun snakeName() : String = "Snake Classic"
    override fun snakeDescription() : String = "Nokia Classic Snake Game"
    override fun tekkenEightName() : String = "Tekken Puzzle"
    override fun tekkenEightDescription() : String = "Puzzles from the popular Tekken game with over 70 characters."
    override fun sudokuSolverName() : String = "Sudoku Solver"
    override fun sudokuSolverDescription() : String = "Using this app, you can solve all Sudoku puzzles with one click. Completely offline and without ads."

    override fun githubRepoTitle(): String = "Github Link"

    override fun getParameterTitle(paramKey: String) : String {
        return when(paramKey){
            "x1" -> "X1"
            "x2" -> "X2"
            "y1" -> "Y1"
            "y2" -> "Y2"
            "dis" -> "Distance (m)"
            "q" -> "Flow Rate (m³/s)"
            "d" -> "Pipe diameter (mm)"
            "v" -> "Flow velocity (m/s)"
            "n" -> "Number of bends"
            "f" -> "Correction factor"
            "l" -> "Pipe length (m)"
            "c" -> "Hazen-Williams coefficient"
            "h" -> "Head loss"
            else -> paramKey
        }
    }
}