package ir.sbpro.waterengineering.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import ir.sbpro.waterengineering.utils.dxp
import androidx.core.net.toUri

@Composable
fun LinkedText(text: String, url: String, color: Color = Color.Black) {
    val context = LocalContext.current

    Text(
        text = text,
        color = color,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(8.dxp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            }
    )
}