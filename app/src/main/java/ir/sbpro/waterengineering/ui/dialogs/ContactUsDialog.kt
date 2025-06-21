package ir.sbpro.waterengineering.ui.dialogs

import android.content.Context
import androidx.compose.runtime.Composable
import ir.sbpro.waterengineering.models.AppSettings

@Composable
public fun ContactUsDialog(
    appSettings: AppSettings,
    context: Context,
    active: Boolean,
    animVisible: Boolean,
    onDismiss: () -> Unit
) {
    AvtContactUsDialog(
        appSettings = appSettings,
        context = context,
        active = active,
        animVisible = animVisible,
        telegramId = "sadeghb97",
        instagramId = "sadegh97b",
        gmail = "sadeghb97@gmail.com",
        onDismiss = onDismiss
    )
}