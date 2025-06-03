package com.example.chensproject

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth

class QueueReminderReceiver : BroadcastReceiver() {
    private lateinit var auth: FirebaseAuth

    override fun onReceive(context: Context, intent: Intent) {
        auth = FirebaseAuth.getInstance()
        val queueTime = intent.getStringExtra("queueTime") ?: ""
        val notification = NotificationCompat.Builder(context, "queue_channel")
            .setContentTitle("תזכורת לתור")
            .setContentText("התור שלך מתקרב! בשעה $queueTime")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        NotificationManagerCompat.from(context).notify(1001, notification)
    }
} 