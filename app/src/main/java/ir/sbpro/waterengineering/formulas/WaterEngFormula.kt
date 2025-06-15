package ir.sbpro.waterengineering.formulas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue

abstract class WaterEngFormula(val formulaKey: String, val parameters: List<String>) {
    fun paramsLen() : Int {
        return parameters.size
    }

    abstract fun getResults(parametersState: ParametersState) : List<FormulaResult>
}

data class FormulaResult(
    val key: String,
    val value: Float
)

class ParametersState(val params: List<MutableState<TextFieldValue>>, val focuses: List<FocusRequester>){
    companion object {
        fun getParams(formulaKey: String, size: Int) : ParametersState {
            val paramsList = arrayListOf<MutableState<TextFieldValue>>()
            val focusesList = arrayListOf<FocusRequester>()

            for(i in 0 until size){
                paramsList.add(mutableStateOf(TextFieldValue("")))
                focusesList.add(FocusRequester())
            }

            return ParametersState(paramsList, focusesList)
        }
    }
}