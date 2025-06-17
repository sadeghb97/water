package ir.sbpro.waterengineering.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.ui.dialogs.AboutUsDialog
import ir.sbpro.waterengineering.ui.dialogs.ContactUsDialog
import ir.sbpro.waterengineering.ui.navigations.Fragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    lang: AppLanguage,
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val nopShow = remember { mutableStateOf(false) }
    val aboutUsShow = remember { mutableStateOf(false) }
    val contactUsShow = remember { mutableStateOf(false) }
    val noNetShow = remember { mutableStateOf(false) }
    val aboutAnimationVisible = remember { mutableStateOf(false) }
    val contactAnimationVisible = remember { mutableStateOf(false) }

    val dialogShowing = nopShow.value || aboutUsShow.value || contactUsShow.value ||
            noNetShow.value || drawerState.isOpen
    val drItemBGC = NavigationDrawerItemDefaults.colors(
        unselectedContainerColor = Color(0xFFE5D9D9)
    )
    //val platformName = AppPlatform.getSummaryPlatformName(lang)

    val defaultDismiss: () -> Unit = {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    AvtDrawer(
        drawerState = drawerState,
        bgPainter = painterResource(R.drawable.drawerbg),
        drBGC = Color(0xFFE5D9D9),
        appName = lang.appName(),
        //userTier = UserTier.BASIC,
        dialogShowing = dialogShowing,
        dialogCloser = {
            nopShow.value = false
            aboutUsShow.value = false
            contactUsShow.value = false
            noNetShow.value = false
        },
        drawerContent = {
            NavigationDrawerItem(
                colors = drItemBGC,
                label = { Text(text = lang.settings()) },
                selected = false,
                onClick = {
                    defaultDismiss()
                    navController.navigate(Fragment.settings.route)
                }
            )

            NavigationDrawerItem(
                colors = drItemBGC,
                label = { Text(text = lang.contactUs()) },
                selected = false,
                onClick = {
                    defaultDismiss()
                    contactUsShow.value = true
                    contactAnimationVisible.value = true
                }
            )

            NavigationDrawerItem(
                colors = drItemBGC,
                label = { Text(text = lang.aboutApp()) },
                selected = false,
                onClick = {
                    defaultDismiss()

                    coroutineScope.launch {
                        delay(100)
                        aboutUsShow.value = true
                        delay(300)
                        aboutAnimationVisible.value = true
                    }
                }
            )
        },
        content = content
    )

    AboutUsDialog(lang = lang, active = aboutUsShow.value, animationVisible = aboutAnimationVisible.value) {
        coroutineScope.launch {
            aboutAnimationVisible.value = false
            aboutUsShow.value = false
        }
    }

    ContactUsDialog(context = context, active = contactUsShow.value, animVisible = contactAnimationVisible.value) {
        contactAnimationVisible.value = false
        contactUsShow.value = false
    }
}