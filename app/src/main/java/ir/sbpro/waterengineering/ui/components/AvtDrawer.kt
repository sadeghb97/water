package ir.sbpro.waterengineering.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.utils.dxp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvtDrawer(
    appSettings: AppSettings,
    drawerState: DrawerState,
    bgPainter: Painter,
    drBGC: Color,
    appName: String,
    //userTier: UserTier,
    dialogShowing: Boolean,
    dialogCloser: () -> Unit,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    BackHandler(enabled = dialogShowing){
        dialogCloser()
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = drBGC,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painter = bgPainter,
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(end = 20.dxp)
                                .fillMaxWidth()
                        ){
                            Text(appName, color = appSettings.darkColor, modifier = Modifier.padding(16.dxp))

                            /*if(userTier == UserTier.PREMIUM){
                                Image(
                                    painter = AvtImageResources.rememberGem(),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.height(30.dxp)
                                        .width(30.dxp)
                                )
                            }*/
                        }
                        Divider()

                       drawerContent()
                    }
                }
            }
        },
    ) {
        content()
    }
}