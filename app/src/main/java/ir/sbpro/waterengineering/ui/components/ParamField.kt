package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import ir.sbpro.waterengineering.utils.sxp
import kotlinx.coroutines.awaitCancellation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ParamField(label: String,
               textState: MutableState<TextFieldValue>,
               modifier: Modifier = Modifier,
               focusRequester: FocusRequester? = null,
               readOnly: Boolean = false,
               onClick: () -> Unit
)
{
    val finalFocusRequester = focusRequester ?: remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    val labelLightColor = Color.White
    val labelDarkColor = Color.Black

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Press) {
                onClick()
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        InterceptPlatformTextInput(
            interceptor = { request, nextHandler ->
                awaitCancellation()
            },
            content = {
                OutlinedTextField(
                    value = textState.value,
                    onValueChange = {
                        val filtered = it.text.filter { char -> char.isDigit() || char.toString() == "." }
                        textState.value = it.copy(text = filtered)
                    },
                    modifier = modifier
                        .focusRequester(finalFocusRequester),
                    interactionSource = interactionSource,
                    label = { Text(text = label, fontSize = 12.sxp) },
                    singleLine = true,
                    readOnly = readOnly,
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = labelLightColor,
                        unfocusedLabelColor = if(textState.value.text.isEmpty()) labelDarkColor else labelLightColor
                    )
                )
            }
        )
    }
}