package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.formulas.WaterEngFormula
import ir.sbpro.waterengineering.utils.copyToClipboard
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.getTextFromClipboard
import ir.sbpro.waterengineering.utils.sxp


@Composable
fun NumberPad(
    activeParam: MutableState<TextFieldValue>?,
    formulas: List<WaterEngFormula>,
    onFormulaChange: (Int) -> Unit,
    onNextParameter: () -> Unit,
    modifier: Modifier = Modifier,
    onCalculate: () -> Unit,
) {
    val context = LocalContext.current

    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9", "0"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .padding(vertical = 12.dxp, horizontal = 40.dxp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dxp),
        horizontalArrangement = Arrangement.spacedBy(12.dxp),
        userScrollEnabled = false
    ) {
        formulas.forEachIndexed { index, item ->
            item {
                KeyTextBox(item.formulaKey.uppercase()) {
                    onFormulaChange(index)
                }
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .padding(vertical = 12.dxp, horizontal = 40.dxp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dxp),
        horizontalArrangement = Arrangement.spacedBy(12.dxp),
        userScrollEnabled = false
    ) {
        val updateTextValue: (String) -> Unit = {
            if(activeParam != null) {
                val oldValue = activeParam.value
                val cursorPosition = oldValue.selection.start

                val newText = buildString {
                    append(oldValue.text.substring(0, cursorPosition))
                    append(it)
                    append(oldValue.text.substring(cursorPosition))
                }

                val newCursorPosition = cursorPosition + it.length
                activeParam.value = TextFieldValue(
                    text = newText,
                    selection = TextRange(newCursorPosition)
                )
            }
        }

        items(buttons.size) { index ->
            KeyTextBox(buttons[index]) {
                updateTextValue(buttons[index])
            }
        }

        item() {
            KeyTextBox(".") {
                updateTextValue(".")
            }
        }

        item() {
            KeySymbolBox(R.drawable.backspace) {
                if(activeParam != null) {
                    val value = activeParam.value
                    val cursorPos = value.selection.start
                    val text = value.text

                    if (cursorPos > 0) {
                        val newText = buildString {
                            append(text.substring(0, cursorPos - 1))
                            append(text.substring(cursorPos))
                        }

                        activeParam.value = TextFieldValue(
                            text = newText,
                            selection = TextRange(cursorPos - 1)
                        )
                    }
                }
            }
        }

        /*item(span = { GridItemSpan(2) }) {
            NumberKeyBox("->") {
                onNextParameter()
            }
        }*/

        item {
            KeySymbolBox(R.drawable.arrow_right) {
                onNextParameter()
            }
        }

        item {
            KeySymbolBox(R.drawable.content_copy) {
                activeParam?.let {
                    copyToClipboard(context, "Parameter", it.value.text)
                }
            }
        }

        item {
            KeySymbolBox(R.drawable.content_paste) {
                activeParam?.let { param ->
                    getTextFromClipboard(context)?.let { clipText ->
                        param.value = param.value.copy(clipText)
                    }
                }
            }
        }

        item {
            KeySymbolBox(R.drawable.function) {
                onCalculate()
            }
        }
    }
}

@Composable
fun KeyBox(onClick: () -> Unit, content: @Composable BoxScope.() -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dxp)
            .clip(RoundedCornerShape(12.dxp))
            .background(Color(0xFFEEEEEE))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun KeyTextBox(text: String, onClick: () -> Unit){
    KeyBox(onClick = onClick) {
        Text(
            text = text,
            fontSize = 24.sxp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun KeySymbolBox(imageId: Int, onClick: () -> Unit){
    KeyBox(onClick = onClick) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.size(24.dxp),
        )
    }
}