package ir.sbpro.waterengineering.formulas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import ir.sbpro.waterengineering.AppSingleton

abstract class WaterEngFormula(val formulaKey: String, val symbol: String, val parameters: List<String>) {
    fun paramsLen() : Int {
        return parameters.size
    }

    abstract fun getResults(parametersState: ParametersState) : List<FormulaResult>
}

data class FormulaResult(
    val key: String,
    val value: Float?
)

class ParametersState(val params: List<MutableState<TextFieldValue>>, val focuses: List<FocusRequester>){
    companion object {
        fun getParams(formula: WaterEngFormula) : ParametersState {
            val appDataStore = AppSingleton.getInstance().appDataStore
            val paramsList = arrayListOf<MutableState<TextFieldValue>>()
            val focusesList = arrayListOf<FocusRequester>()

            formula.parameters.forEachIndexed { index, param ->
                val pureValue = appDataStore.getParamSavedValue(param)
                val intValue = pureValue.toInt()
                val hasDecimal = pureValue % 1.0f != 0.0f

                paramsList.add(mutableStateOf(TextFieldValue(
                    if(hasDecimal) pureValue.toString() else intValue.toString()
                )))
                focusesList.add(FocusRequester())
            }

            return ParametersState(paramsList, focusesList)
        }

        fun safeFloat(str: String) : Float {
            return try {
                str.toFloat()
            } catch (ex: Exception){
                1f
            }
        }
    }
}