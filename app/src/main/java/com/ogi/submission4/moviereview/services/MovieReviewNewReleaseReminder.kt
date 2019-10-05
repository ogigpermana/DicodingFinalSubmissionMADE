package com.ogi.submission4.moviereview.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.presenter.setting.SettingPresenter
import com.ogi.submission4.moviereview.utils.DateTimeUtils
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.NotificationBuilder
import com.ogi.submission4.moviereview.view.settings.SettingCallback

class MovieReviewNewReleaseReminder: BroadcastReceiver(), SettingCallback {
    private var mMovies: MutableList<MovieData> = mutableListOf()

    override fun onReceive(context: Context, intent: Intent) {
        val settingPresenter = SettingPresenter(context, this)
        settingPresenter.getNewReleaseMovies(DateTimeUtils.currentDate())
    }

    override fun getNewReleaseMovieList(context: Context, movies: List<MovieData>) {
        mMovies.clear()
        mMovies.addAll(movies)
        NotificationBuilder.sendNewReleaseNotification(context, movies)
    }

    fun setNewReleaseReminder(context: Context){
        val intent = Intent(context, MovieReviewNewReleaseReminder::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeAlarm = DateTimeUtils.time(MovieReviewConst.ALARM_NEW_RELEASE_HOUR, MovieReviewConst.ALARM_NEW_RELEASE_MINUTE)

        val pendingIntent = PendingIntent.getBroadcast(context, MovieReviewConst.ALARM_NEW_RELEASE_ID, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAlarm.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelNewReleaseReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent(context))
    }

    private fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MovieReviewNewReleaseReminder::class.java)
        return PendingIntent.getBroadcast(context, MovieReviewConst.ALARM_NEW_RELEASE_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}