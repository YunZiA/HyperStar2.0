package com.yunzia.hyperstar.ui.base

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import com.yunzia.hyperstar.ui.base.theme.HyperStarTheme
import com.yunzia.hyperstar.utils.LanguageHelper
import com.yunzia.hyperstar.utils.LanguageHelper.Companion.getIndexLanguage
import com.yunzia.hyperstar.utils.PreferencesUtil
import java.util.Locale

abstract class BaseActivity : ComponentActivity() {

    @Composable abstract fun InitView(colorMode: MutableState<Int>?)
    abstract fun initData(savedInstanceState: Bundle?)

    fun setLocale(
        language : String,
        country: String
    ){
        //locale.value=Locale(language,country)
        val res = this.resources
        val config = res.configuration
        Locale.setDefault(Locale(language,country))
        config.setLocale(Locale(language,country))
        //attachBaseContext(this)
        res.updateConfiguration(config,res.displayMetrics)

        recreate()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
        setContent {
//
            val colorMode = remember { mutableIntStateOf(PreferencesUtil.getInt("color_mode",0)) }

            val darkMode = colorMode.intValue == 2 || (isSystemInDarkTheme() && colorMode.intValue == 0)
            DisposableEffect(darkMode) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT) { darkMode },
                    navigationBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT) { darkMode },)
                window.isNavigationBarContrastEnforced = false  // Xiaomi moment, this code must be here
                onDispose {}
            }
            HyperStarTheme(colorMode.intValue) {
                InitView(colorMode)
            }
        }
    }




    override fun attachBaseContext(newBase: Context?) {
        PreferencesUtil.getInstance().init(newBase)
        if(newBase == null) {
            super.attachBaseContext(newBase)
            return
        }
        super.attachBaseContext(LanguageHelper.wrap(newBase));

    }

    override fun onStart() {
        super.onStart()
        val res = this.resources;
        val configuration = res.configuration;
        val metrics: DisplayMetrics = res.displayMetrics
        val index = PreferencesUtil.getInt("app_language",0)

        configuration.setLocale(getIndexLanguage(index))
        res.updateConfiguration(configuration,metrics)

    }


}


