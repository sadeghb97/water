package ir.sbpro.waterengineering.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.awaitCancellation
import androidx.compose.foundation.interaction.PressInteraction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ParamField(label: String,
               textState: MutableState<TextFieldValue>,
               focusRequester: FocusRequester,
               modifier: Modifier = Modifier,
               onClick: () -> Unit
)
{
    val interactionSource = remember { MutableInteractionSource() }

    // Collect interactions
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Press) {
                onClick()
            }
        }
    }

    InterceptPlatformTextInput(
        interceptor = { request, nextHandler ->
            awaitCancellation()
        },
        content = {
            OutlinedTextField(
                value = textState.value,
                onValueChange = {
                    val filtered = it.text.filter { char -> /*char.isDigit()*/ true }
                    textState.value = it.copy(text = filtered)
                },
                modifier = modifier
                    .focusRequester(focusRequester),
                interactionSource = interactionSource,
                label = { Text(label) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    )

    CompositionLocalProvider(
        LocalTextInputService provides null
    ) {

    }
}