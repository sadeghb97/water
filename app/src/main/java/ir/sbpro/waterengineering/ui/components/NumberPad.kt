package ir.sbpro.waterengineering.ui.components

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.formulas.WaterEngFormula
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.utils.copyToClipboard
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.getTextFromClipboard
import ir.sbpro.waterengineering.utils.sxp
import ir.sbpro.waterengineering.utils.triggerVibration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NumberPad(
    lang: AppLanguage,
    focusManager: FocusManager,
    activeParam: MutableState<TextFieldValue>?,
    formulas: List<WaterEngFormula>,
    onFormulaChange: (Int) -> Unit,
    onNextParameter: () -> Unit,
    modifier: Modifier = Modifier,
    onCalculate: () -> Unit,
) {
    val context = LocalContext.current
    val cs = rememberCoroutineScope()

    val normalStyle = NumPadStyle()
    val formulaStyle = NumPadStyle(
        bgColor = Color(0xFF26A1BD),
        fgColor = Color.White
    )
    val calcStyle = NumPadStyle(
        bgColor = Color(0XFFEE66DD),
        fgColor = Color.White
    )

    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9", "0"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            modifier = Modifier.padding(top = 12.dxp, start = 30.dxp, end = 30.dxp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = modifier.padding(end = 6.dxp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dxp),
                horizontalArrangement = Arrangement.spacedBy(12.dxp),
                userScrollEnabled = false
            ) {
                formulas.forEachIndexed { index, item ->
                    item {
                        KeyTextBox(cs, focusManager, item.symbol, formulaStyle) {
                            onFormulaChange(index)
                        }
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = modifier.padding(horizontal = 6.dxp).weight(3.25f),
                verticalArrangement = Arrangement.spacedBy(12.dxp),
                horizontalArrangement = Arrangement.spacedBy(12.dxp),
                userScrollEnabled = false
            ) {
                val updateTextValue: (String) -> Unit = {
                    if (activeParam != null) {
                        val selection = activeParam.value.selection
                        if (selection.start != selection.end) {
                            val newText = buildString {
                                append(activeParam.value.text.substring(0, selection.start))
                                append(it)
                                append(activeParam.value.text.substring(selection.end))
                            }
                            val newCursor = selection.start + it.length
                            activeParam.value = TextFieldValue(
                                text = newText,
                                selection = TextRange(newCursor)
                            )
                        }
                        else {
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
                }

                items(buttons.size) { index ->
                    KeyTextBox(cs, focusManager, buttons[index], normalStyle) {
                        updateTextValue(buttons[index])
                    }
                }

                item() {
                    KeyTextBox(cs, focusManager, ".", normalStyle) {
                        if (activeParam != null && !activeParam.value.text.contains("."))
                            updateTextValue(".")
                    }
                }

                item() {
                    KeySymbolBox(cs, focusManager, R.drawable.backspace, normalStyle, onLongClick = {
                        if (activeParam != null) {
                            activeParam.value = TextFieldValue(
                                text = "",
                                selection = TextRange(0)
                            )
                        }
                    }) {
                        if (activeParam != null) {
                            val selection = activeParam.value.selection
                            if (selection.start != selection.end) {
                                val newText = buildString {
                                    append(activeParam.value.text.substring(0, selection.start))
                                    append(activeParam.value.text.substring(selection.end))
                                }
                                val newCursor = selection.start
                                activeParam.value = TextFieldValue(
                                    text = newText,
                                    selection = TextRange(newCursor)
                                )
                            }
                            else {
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
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = modifier.padding(start = 6.dxp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dxp),
                horizontalArrangement = Arrangement.spacedBy(12.dxp),
                userScrollEnabled = false
            ) {
                item {
                    KeySymbolBox(cs, focusManager, R.drawable.content_copy, normalStyle) {
                        activeParam?.let {
                            copyToClipboard(context, "Parameter", it.value.text)
                            triggerVibration(context, 50, 50)
                        }
                    }
                }

                item {
                    KeySymbolBox(cs, focusManager, R.drawable.content_paste, normalStyle) {
                        activeParam?.let { param ->
                            getTextFromClipboard(context)?.let { clipText ->
                                param.value = param.value.copy(
                                    text = clipText,
                                    selection = TextRange(clipText.length)
                                )
                                triggerVibration(context, 50, 50)
                            }
                        }
                    }
                }

                item {
                    KeySymbolBox(
                        cs,
                        focusManager,
                        imageId = if(lang.getLayoutDirection() == LayoutDirection.Ltr) R.drawable.arrow_right
                        else R.drawable.arrow_left,
                        normalStyle
                    ) {
                        onNextParameter()
                    }
                }

                item {
                    KeySymbolBox(cs, focusManager, R.drawable.function, calcStyle) {
                        onCalculate()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyBox(
    coroutineScope: CoroutineScope,
    focusManager: FocusManager,
    numPadStyle: NumPadStyle,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    val pressCounter = remember { mutableIntStateOf(0) }
    val pressedTime = remember { mutableLongStateOf(0L) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dxp)
            .clip(RoundedCornerShape(12.dxp))
            .background(numPadStyle.bgColor)
            .indication(interactionSource, ripple())
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        interactionSource.tryEmit(PressInteraction.Press(Offset.Zero))
                        pressedTime.longValue = System.currentTimeMillis()
                        pressCounter.intValue++
                        val pv = pressCounter.intValue
                        coroutineScope.launch {
                            delay(750)
                            if(pressCounter.intValue == pv && pressedTime.longValue > 0){
                                onLongClick()
                                pressedTime.longValue = 0L
                            }
                        }
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        interactionSource.tryEmit(PressInteraction.Release(PressInteraction.Press(Offset.Zero)))
                        if(pressedTime.longValue > 0){
                            onClick()
                            pressedTime.longValue = 0L
                        }
                        true
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        interactionSource.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
                        true
                    }
                    else -> false
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun KeyTextBox(
    coroutineScope: CoroutineScope,
    focusManager: FocusManager,
    text: String,
    numPadStyle: NumPadStyle,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
){
    KeyBox(coroutineScope = coroutineScope,
        focusManager = focusManager,
        numPadStyle = numPadStyle,
        onClick = onClick,
        onLongClick = onLongClick
    ) {
        Text(
            text = text,
            color = numPadStyle.fgColor,
            fontSize = 22.sxp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun KeySymbolBox(
    coroutineScope: CoroutineScope,
    focusManager: FocusManager,
    imageId: Int,
    numPadStyle: NumPadStyle,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
){
    KeyBox(
        coroutineScope = coroutineScope,
        focusManager = focusManager,
        numPadStyle = numPadStyle,
        onClick = onClick,
        onLongClick = onLongClick
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(numPadStyle.fgColor),
            modifier = Modifier.size(24.dxp),
        )
    }
}

data class NumPadStyle(
    val bgColor: Color = Color.White,
    val fgColor: Color = Color.Black
)