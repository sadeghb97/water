package ir.sbpro.waterengineering.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@SuppressLint("ContextCastToActivity")
@Composable
fun TransparentNavigationBar() {
    val activity = LocalContext.current as? Activity ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        activity.window.setDecorFitsSystemWindows(false)
        activity.window.navigationBarColor = TRANSPARENT
    } else {
        @Suppress("DEPRECATION")
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
        @Suppress("DEPRECATION")
        activity.window.navigationBarColor = TRANSPARENT
    }
}

fun getCurrentSystemLanguage(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Resources.getSystem().configuration.locales[0].language
    } else {
        Resources.getSystem().configuration.locale.language
    }
}

fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}

fun getTextFromClipboard(context: Context): String? {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = clipboard.primaryClip
    if (clip != null && clip.itemCount > 0) {
        val item = clip.getItemAt(0)
        return item.text?.toString()
    }
    return null
}

fun triggerVibration(context: Context, durationMs: Long = 50, amplitude: Int = 80) {
    val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    vibrator?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(durationMs, amplitude)
            it.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            it.vibrate(durationMs)
        }
    }
}



