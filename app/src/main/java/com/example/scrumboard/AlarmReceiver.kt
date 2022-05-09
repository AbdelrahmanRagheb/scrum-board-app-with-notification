package com.example.scrumboard

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class AlarmReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
     // val i = Intent(context, DestinationActivity::class.java)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        Log.d("TAG", "onReceive: alarm fired up")
      val title = intent?.extras?.getString("title")
        val content = intent?.extras?.getString("content")
        val cal= Calendar.getInstance()
        val hour = cal[Calendar.HOUR]
        val min = cal[Calendar.MINUTE]
       // val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        val builder = NotificationCompat.Builder(context!!, "abdelrahman_fathy_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
           // .setContentIntent(pendingIntent)
        val nmc = NotificationManagerCompat.from(context)
        nmc.notify(123, builder.build())
    }
}