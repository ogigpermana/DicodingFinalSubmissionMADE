package com.ogi.submission4.moviereview.view.activities.settings

import android.os.Bundle
import android.view.MenuItem
import com.ogi.submission4.moviereview.view.fragments.settings.SettingFragment

@Suppress("DEPRECATION")
class SettingActivity: AppCompatPreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        fragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}