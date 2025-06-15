package ir.sbpro.waterengineering.formulas

import kotlin.math.pow

class DFormula() : WaterEngFormula("d", listOf("x1", "x2", "y1", "y2")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val x1 = ParametersState.safeFloat(parametersState.params[0].value.text)
        val x2 = ParametersState.safeFloat(parametersState.params[1].value.text)
        val y1 = ParametersState.safeFloat(parametersState.params[2].value.text)
        val y2 = ParametersState.safeFloat(parametersState.params[3].value.text)

        val dResult: Float = ((x1 - x2).pow(2) + (y1 - y2).pow(2)).pow(0.5f)

        return listOf(
            FormulaResult(formulaKey, dResult),
        )
    }
}