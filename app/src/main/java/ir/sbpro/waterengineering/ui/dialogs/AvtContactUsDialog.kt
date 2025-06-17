package ir.sbpro.waterengineering.ui.dialogs

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvtContactUsDialog(
    context: Context,
    active: Boolean,
    telegramId: String,
    instagramId: String,
    gmail: String,
    onDismiss: () -> Unit
) {
    AvtDialog (
        modifier = Modifier.clickable {},
        active = active,
        onDismiss = onDismiss,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 18.dxp, bottom = 18.dxp, start = 16.dxp, end = 16.dxp)
                .fillMaxWidth()
        ) {
            ContactItem(title = "@$telegramId", imageId = R.drawable.ic_telegram) {
                try {
                    val telegramIntent = Intent(Intent.ACTION_VIEW)
                    telegramIntent.data = Uri.parse("tg://resolve?domain=$telegramId")
                    telegramIntent.setPackage("org.telegram.messenger")
                    context.startActivity(telegramIntent)
                } catch (e: Exception) {}
            }

            ContactItem(title = "@$instagramId", imageId = R.drawable.ic_instagram) {
                try {
                    val intentDirect = Intent(Intent.ACTION_SEND)
                    intentDirect.component = ComponentName(
                        "com.instagram.android",
                        "com.instagram.direct.share.handler.DirectShareHandlerActivity"
                    )
                    intentDirect.type = "text/plain"
                    intentDirect.putExtra(Intent.EXTRA_TEXT, "Your message here")
                    context.startActivity(intentDirect)
                } catch (e: Exception) {}
            }

            ContactItem(title = gmail, imageId = R.drawable.ic_gmail) {
                try {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "plain/text"
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(gmail))
                    context.startActivity(Intent.createChooser(intent, ""))
                } catch (e: Exception) {}
            }
        }
    }
}

@Composable
fun ContactItem(title: String, imageId: Int, callback: () -> Unit){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = Modifier
                .padding(vertical = 6.dxp, horizontal = 8.dxp)
                .fillMaxWidth()
                .clickable {
                    callback()
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 6.dxp, horizontal = 6.dxp)
                    .fillMaxWidth()
                    .padding(top = 4.dxp, bottom = 4.dxp, start = 12.dxp, end = 4.dxp)
            ) {
                Image(
                    painter = painterResource(imageId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(48.dxp)
                )

                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dxp).fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge.merge(
                        TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sxp,
                            color = Color.Black
                        )
                    )
                )
            }
        }
    }
}