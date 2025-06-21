package ir.sbpro.waterengineering.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp

@Composable
public fun ColorPickerDialog(
    confirmText: String,
    active: Boolean,
    initColor: Color?,
    onDismiss: () -> Unit,
    onSelectColor: (Color) -> Unit
) {
    if(initColor != null) {
        val appSingleton: AppSingleton = AppSingleton.getInstance()
        var selectedColor = remember { mutableStateOf(initColor) }
        val controller = rememberColorPickerController()

        LaunchedEffect(active) {
            if(active) controller.selectByColor(initColor, false)
        }

        AvtDialog(active = active, onDismiss = onDismiss) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 16.dxp, horizontal = 16.dxp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 24.dxp)
                        .height(40.dxp)
                        .width(100.dxp)
                        .background(selectedColor.value)
                        .border(color = Color.Black, width = 2.dxp)
                )

                HsvColorPicker(
                    controller = controller,
                    modifier = Modifier
                        .padding(top = 24.dxp)
                        .fillMaxWidth()
                        .height(220.dxp),
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        selectedColor.value = colorEnvelope.color
                    }
                )

                Spacer(modifier = Modifier.height(24.dxp))

                BrightnessSlider(
                    modifier = Modifier
                        .padding(horizontal = 30.dxp)
                        .height(40.dxp)
                        .width(220.dxp),
                    controller = controller
                )

                Button(
                    onClick = {
                        onSelectColor(selectedColor.value)
                    },
                    modifier = Modifier
                        .padding(top = 40.dxp, start = 16.dxp, end = 16.dxp, bottom = 30.dxp)
                        .fillMaxWidth()
                        .height(50.dxp)
                ) {
                    Text(
                        text = confirmText,
                        fontSize = 22.sxp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}