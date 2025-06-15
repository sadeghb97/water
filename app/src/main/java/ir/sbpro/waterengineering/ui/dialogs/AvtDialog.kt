package ir.sbpro.waterengineering.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun AvtDialog(
    modifier: Modifier = Modifier,
    active: Boolean,
    onDismiss: () -> Unit,
    okOption: Boolean = false,
    okTitle: String = "اوکی",
    okCallback: () -> Unit = {},
    negativeOption: Boolean = false,
    negativeTitle: String = "نه",
    content: @Composable () -> Unit,
) {
    if (active) {
        Box(
            modifier = Modifier
                .background(Color.DarkGray.copy(alpha = 0.6f))
                .fillMaxSize()
                .clickable {
                    onDismiss()
                }
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 36.dxp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dxp),
            ) {
                Column(
                    modifier = modifier.fillMaxWidth()
                ) {
                    content()

                    if(okOption || negativeOption) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.padding(start = 24.dxp, end = 24.dxp, bottom = 16.dxp).fillMaxWidth()
                        ) {
                            if(negativeOption){
                                Text(
                                    text = negativeTitle,
                                    modifier = Modifier.padding(start = 48.dxp).clickable {
                                        onDismiss()
                                    },
                                    style = MaterialTheme.typography.bodyLarge.merge(
                                        TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sxp,
                                            color = Color(0xFFD0BCFF)
                                        )
                                    )
                                )
                            }

                            if(okOption) {
                                Text(
                                    text = okTitle,
                                    modifier = Modifier.padding(start = 48.dxp).clickable {
                                        okCallback()
                                        onDismiss()
                                    },
                                    style = MaterialTheme.typography.bodyLarge.merge(
                                        TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sxp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}