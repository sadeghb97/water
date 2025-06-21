package ir.sbpro.waterengineering.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp
import kotlin.math.roundToInt

@Composable
fun SizeSlider(
    lang: AppLanguage,
    label: String,
    initialValue: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {
    var sliderPosition = remember { mutableStateOf(initialValue.toFloat()) }

    val options = listOf(
        lang.sizeOption(0),
        lang.sizeOption(1),
        lang.sizeOption(2),
        lang.sizeOption(3),
        lang.sizeOption(4)
    )

    Column(
        modifier = modifier,
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 21.sxp,
            modifier = Modifier
        )

        Slider(
            value = sliderPosition.value,
            onValueChange = {
                sliderPosition.value = it
                onValueChange(it.roundToInt())
            },
            valueRange = 0f..(options.size - 1).toFloat(),
            steps = options.size - 2,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF215379),
                activeTrackColor = Color(0xFF215379),
                inactiveTrackColor = Color.LightGray
            ),
            modifier = Modifier.padding(horizontal = 8.dxp).fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dxp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            options.forEach { label ->
                Text(
                    text = label,
                    fontSize = 14.sxp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

class SizeConfigConvert(){
    companion object {
        fun optionToValue(option: Int) : Float {
            return when(option){
                0 -> 0.75f
                1 -> 0.875f
                2 -> 1f
                3 -> 1.125f
                else -> 1.25f
            }
        }

        fun valueToOption(value: Float) : Int {
            return when(value){
                0.75f -> 0
                0.875f -> 1
                1f -> 2
                1.125f -> 3
                else -> 4
            }
        }
    }
}