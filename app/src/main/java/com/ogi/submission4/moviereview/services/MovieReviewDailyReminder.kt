package com.ogi.submission4.moviereview.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.utils.DateTimeUtils
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.NotificationBuilder

class MovieReviewDailyReminder : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationBuilder.sendDailyNotification(context, context.getString(R.string.notification_content), MovieReviewConst.NOTIFICATION_ID)
    }

    fun setRepeatingReminder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeAlarm = DateTimeUtils.time(MovieReviewConst.ALARM_HOUR, MovieReviewConst.ALARM_MINUTE)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAlarm.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent(context))
    }

    fun cancelRepeatingReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent(context))
    }

    private fun pendingIntent(context: Context): PendingIntent{
        val intent = Intent(context, MovieReviewDailyReminder::class.java)
        return PendingIntent.getBroadcast(context, MovieReviewConst.ALARM_REPEATING_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}