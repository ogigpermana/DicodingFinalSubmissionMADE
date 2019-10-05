package com.ogi.submission4.moviereview.view.fragments.settings


import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.services.MovieReviewDailyReminder
import com.ogi.submission4.moviereview.services.MovieReviewNewReleaseReminder

@Suppress("DEPRECATION")
class SettingFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {
    private var dailyReminder = MovieReviewDailyReminder()
    private var newReleaseReminder = MovieReviewNewReleaseReminder()
    private lateinit var ctx: Context

    override fun onPreferenceChange(preference: Preference?, component: Any?): Boolean {
        val key = preference?.key
        val isChecked = component as Boolean

        when (key) {
            getString(R.string.daily_reminder_key) -> if (isChecked) dailyReminder.setRepeatingReminder(ctx) else dailyReminder.cancelRepeatingReminder(ctx)
            getString(R.string.new_release_key) -> if (isChecked) newReleaseReminder.setNewReleaseReminder(ctx) else newReleaseReminder.cancelNewReleaseReminder(ctx)
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        if(activity != null){
            findPreference(getString(R.string.daily_reminder_key)).onPreferenceChangeListener = this
            findPreference(getString(R.string.new_release_key)).onPreferenceChangeListener = this
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
}
