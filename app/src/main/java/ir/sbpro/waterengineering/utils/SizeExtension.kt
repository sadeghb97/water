package ir.sbpro.waterengineering.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

object SizeInterface {
    var sh: Int = 0
    var sw: Int = 0
    var shr: Double = 1.0
    var swr: Double = 1.0
    var minsm: Double = 1.0
    var userFontScale = 1f

    @Composable
    fun ExtendScreenSize(){
        userFontScale = LocalDensity.current.fontScale

        val configuration = LocalConfiguration.current
        sh = configuration.screenHeightDp
        sw = configuration.screenWidthDp

        println("XQQQSize: ($sh - $sw)")

        shr = sh.toDouble() / 795
        swr = sw.toDouble() / 393
        minsm = if(shr < swr) shr else swr
    }
}

val Int.dxp
    get() = ceil(this.toDouble() * SizeInterface.minsm).toInt().dp

val Int.dwp
    get() = ceil(this.toDouble() * SizeInterface.swr).toInt().dp

val Int.sxp
    get() = ceil(this.toDouble() * SizeInterface.minsm / SizeInterface.userFontScale).toInt().sp

val Int.swp
    get() = ceil(this.toDouble() * SizeInterface.swr / SizeInterface.userFontScale).toInt().sp