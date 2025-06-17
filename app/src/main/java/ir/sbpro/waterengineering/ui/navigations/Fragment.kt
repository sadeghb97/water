package ir.sbpro.waterengineering.ui.navigations

sealed class Fragment (val route: String) {
    object main : Fragment("main")
    object settings : Fragment("settings")
}
