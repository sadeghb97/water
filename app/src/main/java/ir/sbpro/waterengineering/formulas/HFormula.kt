package ir.sbpro.waterengineering.formulas

import kotlin.math.pow

class HFormula() : WaterEngFormula("h", "h", listOf("f", "l", "q", "c", "d")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val f = ParametersState.safeFloat(parametersState.params[0].value.text)
        val l = ParametersState.safeFloat(parametersState.params[1].value.text)
        val q = ParametersState.safeFloat(parametersState.params[2].value.text)
        val c = ParametersState.safeFloat(parametersState.params[3].value.text)
        val d = ParametersState.safeFloat(parametersState.params[4].value.text)

        val hResult: Float = 163021.8f * f * l * (q / c).pow(1.852f) * (d / 10).pow(-4.87f)

        return listOf(
            FormulaResult(formulaKey, hResult)
        )
    }
}