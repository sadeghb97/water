package ir.sbpro.waterengineering.formulas

import kotlin.math.PI
import kotlin.math.pow

class VFormula() : WaterEngFormula("v", listOf("q", "d")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val q = ParametersState.safeFloat(parametersState.params[0].value.text)
        val d = ParametersState.safeFloat(parametersState.params[1].value.text)

        val vResult: Float = (4000 * q) / (PI.toFloat() * d.pow(2))

        return listOf(
            FormulaResult(formulaKey, vResult),
        )
    }
}