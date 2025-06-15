package ir.sbpro.waterengineering.formulas

class DFormula() : WaterEngFormula("d", listOf("x1", "x2", "y1", "y2")) {
    override fun getResults(parametersState: ParametersState): List<FormulaResult> {
        val dResult: Float = 4f

        return listOf(
            FormulaResult(formulaKey, dResult),
        )
    }
}