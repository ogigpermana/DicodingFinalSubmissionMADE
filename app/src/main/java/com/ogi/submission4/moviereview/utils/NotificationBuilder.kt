package com.ogi.submission4.moviereview.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.utils.MovieReviewConst.IMG_URL
import com.ogi.submission4.moviereview.view.MainActivity
import com.ogi.submission4.moviereview.view.activities.movie.MovieDetailActivity
import com.squareup.picasso.Picasso

class NotificationBuilder(var context: Context) {
    companion object{
        fun sendDailyNotification(context: Context, message: String, notifId: Int){
            val intent = Intent(context, MainActivity::class.java)
            notification(context, intent, message, notifId)
        }
        private fun notification(context: Context, intent: Intent, message: String, notifId: Int){
            val pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, MovieReviewConst.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
                .setContentTitle(context.resources.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(alarmSound)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    MovieReviewConst.CHANNEL_ID,
                    MovieReviewConst.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                channel.enableLights(true)

                builder.setChannelId(MovieReviewConst.CHANNEL_ID)
                notificationManager.createNotificationChannel(channel)
            }

            val notification = builder.build()

            notificationManager.notify(notifId, notification)
        }

        fun sendNewReleaseNotification(context: Context, movies: List<MovieData>){
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder

            if(movies.count() < MovieReviewConst.MAX_NOTIF){
                movies.forEach {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra(MovieReviewConst.EXTRA_MOVIE, it)
                    val banner = Picasso
                        .get()
                        .load(IMG_URL+it.backdropPath)
                    notification(context, intent, context.getString(R.string.new_release_notification, it.originalTitle+"\n"+banner), it.id!!)
                }
            } else {

                val inboxStyle = NotificationCompat.InboxStyle()
                    .setBigContentTitle(movies.count().toString() + " new releases")
                    .setSummaryText(context.getString(R.string.app_name))

                movies.forEach {
                    inboxStyle.addLine(it.originalTitle)
                }

                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(MovieReviewConst.EXTRA_MOVIE, movies.first())
                val pendingIntent = PendingIntent.getActivity(context, MovieReviewConst.STACK_NOTIF_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                builder = NotificationCompat.Builder(context, MovieReviewConst.STACK_CHANNEL_ID)
                    .setContentTitle(movies.count().toString() + " new releases")
                    .setSmallIcon(R.drawable.logo)
                    .setGroup(MovieReviewConst.GROUP_STACK_NOTIF)
                    .setContentIntent(pendingIntent)
                    .setGroupSummary(true)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        MovieReviewConst.STACK_CHANNEL_ID,
                        MovieReviewConst.STACK_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    builder.setChannelId(MovieReviewConst.STACK_CHANNEL_ID)

                    notificationManager.createNotificationChannel(channel)
                }

                val notification = builder.build()
                notificationManager.notify(MovieReviewConst.STACK_NOTIF_ID, notification)

            }
        }
    }
}