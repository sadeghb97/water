package ir.sbpro.waterengineering.lang

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import ir.sbpro.waterengineering.R

class FaLang : AppLanguage {
    override fun getLangCode() = "fa"
    override fun getLayoutDirection() = LayoutDirection.Rtl
    override fun getDefaultFont(): FontFamily { return FontFamily(Font(R.font.iransans_mobile)) }

    override fun appName() = "فرمول‌های مهندسی آب"
    override fun start() = "شروع"
    override fun settings() = "تنظیمات"
    override fun language() = "زبان"
    override fun menu() = "منو"
    override fun version() = "نسخه"

    override fun bronsteinTimeControl() = "تاخیر برونشتین"
    override fun suddenDeathTimeControl() = "مرگ ناگهانی"
    override fun hourglassTimeControl() = "ساعت شنی"
    override fun fischerTimeControl() = "فیشر"
    override fun usDelayTimeControl() = "تاخیر آمریکایی"
    override fun overtimeTimeControl() = "چندمرحله‌ای"

    override fun bronsteinDescription() = "در این روش زمان‌سنجی، پس از هر حرکت مقداری زمان به ساعت بازیکن اضافه می‌شود. اما برخلاف تایم کنترل فیشر، همیشه حداکثر مقدار تعیین‌شده اضافه نمی‌شود. اگر بازیکن بیشتر از مدت تأخیر زمان صرف کند، کل زمان تأخیر به ساعتش افزوده می‌شود، اما اگر سریع‌تر حرکت کند، فقط همان مقدار مصرف‌شده به زمان او اضافه می‌گردد."
    override fun suddenDeathDescription() = "ساده‌ترین تایم کنترل شطرنج است. هر بازیکن مقدار ثابتی زمان برای کل بازی دارد. اگر زمان اصلی یک بازیکن تمام شود، معمولاً بازنده‌ی بازی خواهد بود."
    override fun hourglassDescription() = "در این روش، ساعت هر بازیکن با مقدار مشخصی آغاز می‌شود. هنگام فکر کردن یک بازیکن، زمان از ساعت او کم شده و به ساعت حریف اضافه می‌شود. این سیستم مانند یک ساعت شنی عمل می‌کند: شن از یک سمت خالی شده و سمت دیگر را پر می‌کند. مجموع زمان دو بازیکن همیشه ثابت است و حرکات کند، زمان بیشتری به حریف می‌دهد."
    override fun fischerDescription() = "که با نام‌های افزایش (Increment) یا پاداش (Bonus) نیز شناخته می‌شود. در این روش، پس از هر حرکت مقدار مشخصی زمان به زمان اصلی بازیکن افزوده می‌شود، مگر اینکه زمان بازیکن پیش از اتمام حرکت به صفر برسد. برای مثال، در کنترل زمانی 30+90، به ازای هر حرکت، 30 ثانیه به زمان بازیکن اضافه می‌شود."
    override fun usDelayDescription() = "که با نام تأخیر ساده (Simple Delay) نیز شناخته می‌شود. در این روش، ساعت قبل از شروع کاهش زمان اصلی، به اندازه‌ی مدت تأخیر صبر می‌کند. برای مثال، اگر تأخیر ۱۰ ثانیه باشد، قبل از کم شدن زمان اصلی، ۱۰ ثانیه صبر می‌شود. از نظر ریاضی، تأخیر برونشتاین و تأخیر ساده یکسان هستند، اما نمایش زمان در آن‌ها متفاوت است."
    override fun overtimeDescription() = "در این روش، بازی به چند مرحله تقسیم می‌شود. هر مرحله از تعداد معینی حرکت تشکیل می‌شود. هر مرحله می‌تواند میزان مشخصی پاداش داشته باشد و پس از پایان هر مرحله میزان مشخصی زمان به ساعت بازیکن اضافه می‌شود."

    override fun baseTime() = "زمان پایه"
    override fun rewardTime() = "زمان پاداش"
    override fun delayTime() = "زمان تاخیر"
    override fun stageMoves() = "تعداد حرکت"
    override fun firstStage() = "مرحله اول"
    override fun secondStage() = "مرحله دوم"
    override fun thirdStage() = "مرحله سوم"

    override fun timeControl() = "تایم کنترل"
    override fun chooseTimeControl() = "انتخاب تایم کنترل"
    override fun differentSettingsForPlayers() = "تنظیمات متفاوت برای هر بازیکن"
    override fun enableThirdStage() = "فعالسازی مرحله سوم"
    override fun bothPlayersSettings() = "تنظیمات"
    override fun whitePlayerSettings() = "تنظیمات بازیکن سفید"
    override fun blackPlayerSettings() = "تنظیمات بازیکن سیاه"

    override fun settingKeepScreenOn() = "روشن نگه داشتن صفحه نمایش زمانی که ساعت در حال حرکت است"
    override fun settingShowMoveNumber() = "نمایش شماره حرکت"
    override fun settingShowSide() = "نمایش رنگ بازیکن"
    override fun settingDisplayAccurateTime() = "نمایش زمان با دقت صدم ثانیه"
    override fun settingPlaySoundAfterMove() = "پخش صوت پس از حرکت بازیکن"
    override fun settingVibrateAfterMove() = "پخش ویبره پس از حرکت بازیکن"

    override fun whiteSide() = "بازیکن سفید"
    override fun blackSide() = "بازیکن سیاه"
    override fun winnerSide() = "برنده"
    override fun loserSide() = "بازنده"
    override fun move(): String = "حرکت"
    override fun areYouSureToReset() = "ساعت ریست خواهد شد. آیا مطمئنید؟"
    override fun areYouSureToBack() = "ساعت ریست خواهد شد. آیا مطمئنید؟"
    override fun yes() = "بله"
    override fun no()= "خیر"

    override fun aboutApp()= "درباره برنامه"
    override fun commentIn(): String = "نظر در"
    override fun moreApps(): String = "برنامه‌های بیشتر"
    override fun contactUs(): String = "تماس با ما"
    override fun bazarSummaryName() : String = "بازار"
    override fun bazarFullName() : String = "کافه‌بازار"
    override fun myketName() : String = "مایکت"
    override fun aptoideName() : String = "اپتوید"
    override fun marketWord(): String = "مارکت"

    override fun noPlatformMessage(platform: String): String {
        return "لطفا آخرین نسخه برنامه " + platform + " را نصب کرده و از لاگین به حساب کاربری خود در آن مطمئن شوید"
    }

    override fun puzzlinhoName() : String = "پازلینیو - پازل\u200Cهای فوتبالی "
    override fun puzzlinhoDescription() : String = "پازل\u200Cهای فوتبالی، حدس فوتبالیست، حدس بازیکن، حدس تیم فوتبال، دوئل و رقابت فوتبالی با دوستان به صورت آفلاین"
    override fun cinemaniaName() : String = "سینمانیا"
    override fun cinemaniaDescription() : String = "حدس بهترین فیلم\u200Cهای تاریخ، شامل بیش از 1000 فیلم شاخص دنیای سینما برای شیفتگان هنر هفتم"
    override fun snakeName() : String = "اسنیک کلاسیک"
    override fun snakeDescription() : String = "بازی مار (اسنیک) نوکیا"
    override fun tekkenEightName() : String = "تیکن پازل"
    override fun tekkenEightDescription() : String = "پازل های بازی محبوب تیکن با بیش از 70 کاراکتر."
    override fun sudokuSolverName() : String = "حل کننده سودوکو"
    override fun sudokuSolverDescription() : String = "با استفاده از این برنامه میتونی همه سودوکوهارو با یک کلیک حل کنی. کاملا آفلاین و بدون تبلیغات."

    override fun getParameterTitle(paramKey: String) : String {
        return when(paramKey){
            //"" -> ""
            else -> paramKey
        }
    }

    override fun getFormulaTitle(formulaKey: String) : String {
        return when(formulaKey){
            //"" -> ""
            else -> formulaKey
        }
    }
}