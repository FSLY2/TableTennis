package com.example.tabletennis.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tabletennis.R
import com.example.tabletennis.common.Preferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        Thread.sleep(3000)
        setTheme(R.style.MainTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Preferences initialization
        Preferences.init(this, APP_PREFERENCES)
        //Clear all preferences before startup
//        Preferences.cancel()
    }

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
    }
}