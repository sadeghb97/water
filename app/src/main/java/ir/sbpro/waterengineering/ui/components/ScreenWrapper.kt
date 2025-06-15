package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ir.sbpro.waterengineering.utils.dxp

@Composable
fun ScreenWrapper(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB388FF), Color(0xFF8C9EFF))
                )
            )
            .padding(bottom = 60.dxp)
    ) {
        content()
    }
}