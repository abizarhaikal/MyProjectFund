package com.example.myprojectfund.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.myprojectfund.databinding.ActivitySettingBinding
import com.example.myprojectfund.ui.viewmodel.SettingViewModel
import com.example.myprojectfund.ui.viewmodel.ViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var activitySettingBinding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(activitySettingBinding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(application,pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSetting().observe(this) {isDarkModeActive->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activitySettingBinding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activitySettingBinding.switchTheme.isChecked = false
            }
        }
        activitySettingBinding.switchTheme.setOnCheckedChangeListener { _:CompoundButton?, isChecked:Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }
}