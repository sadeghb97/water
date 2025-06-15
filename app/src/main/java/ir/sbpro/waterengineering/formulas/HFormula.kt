package ir.sbpro.waterengineering.formulas

import kotlin.math.pow

class HFormula() : WaterEngFormula("h", listOf("n", "l", "q", "c", "d")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val n = ParametersState.safeFloat(parametersState.params[0].value.text)
        val l = ParametersState.safeFloat(parametersState.params[1].value.text)
        val q = ParametersState.safeFloat(parametersState.params[2].value.text)
        val c = ParametersState.safeFloat(parametersState.params[3].value.text)
        val d = ParametersState.safeFloat(parametersState.params[4].value.text)

        val fResult: Float = (2 * n / (2 * n - 1)) * (0.3506f + (0.923f / (6 * n.pow(2))))
        val hResult: Float = 163021.8f * fResult * l * (q / c).pow(1.852f) * (d / 10).pow(-4.87f)

        return listOf(
            FormulaResult("f", fResult),
            FormulaResult(formulaKey, hResult)
        )
    }
}