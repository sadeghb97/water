package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.utils.copyToClipboard
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp
import ir.sbpro.waterengineering.utils.triggerVibration

@Composable
fun ResultDisplay(
    lang: AppLanguage,
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    minimal: Boolean = false,
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .padding(6.dxp),
        elevation = CardDefaults.cardElevation(4.dxp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(16.dxp)
    ) {
        val titleStartPadding = if(lang.getLayoutDirection() == LayoutDirection.Ltr) 0.dxp else 12.dxp
        val colStartPadding = if(lang.getLayoutDirection() == LayoutDirection.Ltr) 16.dxp else 4.dxp
        val colEndPadding = if(lang.getLayoutDirection() == LayoutDirection.Ltr) 4.dxp else 16.dxp
        val valueTextSize = if(minimal) 17.sxp else 22.sxp

        Column(
            modifier = Modifier
                .padding(start = colStartPadding, end = colEndPadding, top = 8.dxp, bottom = 8.dxp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = titleStartPadding).fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = value ?: "-",
                        fontWeight = FontWeight.Bold,
                        fontSize = valueTextSize,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            value?.let {
                                copyToClipboard(context, "Result", it)
                                triggerVibration(context, 50, 50)
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.content_copy),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.size(24.dxp),
                        )
                    }
                }
            }
        }
    }
}
