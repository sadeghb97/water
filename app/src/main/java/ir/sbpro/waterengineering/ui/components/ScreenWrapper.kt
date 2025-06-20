package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.utils.dxp

@Composable
fun ScreenWrapper(
    appSettings: AppSettings,
    lang: AppLanguage,
    navController: NavController,
    drawerState: DrawerState?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }

    val box: @Composable () -> Unit = {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFB388FF), Color(0xFF8C9EFF))
                    )
                )
                .padding(bottom = 72.dxp)
                .clickable(indication = null, interactionSource = interactionSource) {
                    onClick()
                }
        ) {
            content()
        }
    }

    if(drawerState != null) {
        AppDrawer(
            appSettings = appSettings,
            lang = lang,
            navController = navController,
            drawerState = drawerState
        ) {
            box()
        }
    }
    else box()
}