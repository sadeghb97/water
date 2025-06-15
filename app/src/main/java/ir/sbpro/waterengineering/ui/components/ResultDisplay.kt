package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.utils.copyToClipboard
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.triggerVibration

@Composable
fun ResultDisplay(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .padding(6.dxp),
        elevation = CardDefaults.cardElevation(4.dxp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(16.dxp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dxp, vertical = 8.dxp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(6.dxp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = value ?: "-",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
