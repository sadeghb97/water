package ir.sbpro.waterengineering.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.utils.dxp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    appSettings: AppSettings,
    active: Boolean,
    description: String,
    positiveTitle: String = "بله",
    negativeTitle: String = "خیر",
    onDismiss: () -> Unit,
    okCallback: () -> Unit
) {
    AvtDialog (
        active = active,
        onDismiss = onDismiss,
        okOption = true,
        okTitle = positiveTitle,
        negativeOption = true,
        negativeTitle = negativeTitle,
        okCallback = okCallback
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dxp, vertical = 24.dxp)
        ) {
            Text(
                text = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dxp)
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                color = appSettings.darkColor
            )
        }
    }
}