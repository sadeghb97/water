package ir.sbpro.waterengineering.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.sbpro.waterengineering.MainActivity
import ir.sbpro.waterengineering.ui.screens.MainScreen
import ir.sbpro.waterengineering.ui.screens.SettingsScreen

@Composable
fun Navigation(activity: MainActivity){
    val navController = rememberNavController()

    navController.addOnDestinationChangedListener { navc, destination, bundle ->
        val destinationRoute = destination.route
        //activity.onRouteChanged(destinationRoute.toString())
    }

    NavHost(navController = navController, startDestination = Fragment.main.route){
        composable(route = Fragment.main.route){
            MainScreen(navController = navController)
        }
        composable(route = Fragment.settings.route){
            SettingsScreen(navController = navController)
        }
    }
}

fun navigateMain(navController: NavController){
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val targetRoute = Fragment.main.route

    if(currentRoute != targetRoute) {
        navController.navigate(targetRoute) {
            popUpTo(targetRoute) {
                inclusive = false
            }
        }
    }
}



