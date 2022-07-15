package com.example.tabletennis.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tabletennis.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Preferences initialization
        Preferences.init(this, APP_PREFERENCES)
        //Clear all preferences before startup
        Preferences.cancel()
    }

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
    }
}