package ir.sbpro.waterengineering.formulas

class VFormula() : WaterEngFormula("v", listOf("q", "d")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val vResult: Float = 4f

        return listOf(
            FormulaResult(formulaKey, vResult),
        )
    }
}