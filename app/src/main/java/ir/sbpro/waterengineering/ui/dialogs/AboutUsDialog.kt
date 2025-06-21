package ir.sbpro.waterengineering.ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.ui.components.LinkedText
import ir.sbpro.waterengineering.utils.dxp

@Composable
public fun AboutUsDialog(
    appSettings: AppSettings,
    lang: AppLanguage,
    active: Boolean,
    animationVisible: Boolean,
    onDismiss: () -> Unit
) {
    val appSingleton: AppSingleton = AppSingleton.getInstance()
    val verName = appSingleton.verName
    val verCode = appSingleton.verCode

    AvtDialog(active = active, onDismiss = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 16.dxp, horizontal = 16.dxp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.matchstick),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(300.dxp)
            )

            Column (
                modifier = Modifier.padding(horizontal = 12.dxp).height(285.dxp).width(300.dxp)
            ) {
                AnimatedVisibility(
                    visible = animationVisible,
                    enter = fadeIn(animationSpec = tween(2000))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.watereng),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(top = 16.dxp).fillMaxSize()
                    )
                }
            }

            Text(
                text = lang.appName(),
                color = appSettings.darkColor,
                modifier = Modifier.padding(12.dxp),
            )
            Text(
                text = lang.version() + " " + "$verName ($verCode)"
            )
            LinkedText(
                text = lang.githubRepoTitle(),
                url = lang.githubRepoUrl(),
                color = appSettings.darkColor
            )
        }
    }
}