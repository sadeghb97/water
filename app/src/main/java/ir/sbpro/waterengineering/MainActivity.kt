package ir.sbpro.waterengineering

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import ir.sbpro.waterengineering.ui.navigations.Navigation
import ir.sbpro.waterengineering.ui.theme.WaterEngineeringTheme
import ir.sbpro.waterengineering.utils.SizeInterface
import ir.sbpro.waterengineering.utils.TransparentNavigationBar

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        val appSingleton = AppSingleton.getInstance(this)
        appSingleton.loadDetails(this)
        val appDataStore = appSingleton.appDataStore

        setContent {
            TransparentNavigationBar()
            SizeInterface.Init()
            val lang by appDataStore.languageFlow.collectAsState(appSingleton.startLanguage)

            CompositionLocalProvider(LocalLayoutDirection provides lang.getLayoutDirection()) {
                WaterEngineeringTheme(lang = lang) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Navigation(this)
                    }
                }
            }
        }
    }
}