package ir.sbpro.waterengineering.formulas

import kotlin.math.pow

class FFormula() : WaterEngFormula("f", "f", listOf("n")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val n = ParametersState.safeFloat(parametersState.params[0].value.text)
        val fResult: Float = (2 * n / (2 * n - 1)) * (0.3506f + (0.923f / (6 * n.pow(2))))

        return listOf(
            FormulaResult("f", fResult)
        )
    }
}