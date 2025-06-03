package com.example.chensproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth

class BusinessQueueReceiver : BroadcastReceiver() {
    private lateinit var auth: FirebaseAuth

    override fun onReceive(context: Context, intent: Intent) {
        auth = FirebaseAuth.getInstance()
        val customerEmail = intent.getStringExtra("customerEmail") ?: ""
        val queueTime = intent.getStringExtra("queueTime") ?: ""
        val notification = NotificationCompat.Builder(context, "queue_channel")
            .setContentTitle("נקבע תור חדש לעסק שלך!")
            .setContentText("לקוח $customerEmail קבע תור ל-$queueTime")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()
    }
} 