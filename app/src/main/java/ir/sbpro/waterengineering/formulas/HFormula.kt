package ir.sbpro.waterengineering.formulas

class HFormula() : WaterEngFormula("h", listOf("n", "l", "q", "c", "d")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val fResult: Float = 4f
        val hResult: Float = 4f

        return listOf(
            FormulaResult("f", fResult),
            FormulaResult(formulaKey, hResult)
        )
    }
}