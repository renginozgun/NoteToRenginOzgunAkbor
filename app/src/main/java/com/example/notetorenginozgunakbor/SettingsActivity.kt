package com.example.notetorenginozgunakbor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.widget.Switch

class SettingsActivity : AppCompatActivity() { //Our second activity
    private var showDividers: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = getSharedPreferences("Note to Self", Context.MODE_PRIVATE) //gets our preferences called Note to Self file
        showDividers = prefs.getBoolean("dividers", true) // Get the preferences but as default, it is true
        val switch1 = findViewById<Switch>(R.id.switch1)  //Get our switch
        switch1.isChecked = showDividers

        switch1.setOnCheckedChangeListener {  //Show dividers if our switch is checked
            button, isChecked ->
            showDividers = isChecked
        }

    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("Note to Self", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("dividers", showDividers)
        editor.apply()  // We apply our changes in here and save our preferences
    }
}