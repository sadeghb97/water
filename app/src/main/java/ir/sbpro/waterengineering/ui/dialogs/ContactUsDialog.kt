package ir.sbpro.waterengineering.ui.dialogs

import android.content.Context
import androidx.compose.runtime.Composable

@Composable
public fun ContactUsDialog(
    context: Context,
    active: Boolean,
    animVisible: Boolean,
    onDismiss: () -> Unit
) {
    AvtContactUsDialog(
        context = context,
        active = active,
        animVisible = animVisible,
        telegramId = "sadeghb97",
        instagramId = "sadegh97b",
        gmail = "sadeghb97@gmail.com",
        onDismiss = onDismiss
    )
}