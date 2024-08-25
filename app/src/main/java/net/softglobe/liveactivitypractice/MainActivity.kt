package net.softglobe.liveactivitypractice

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import net.softglobe.liveactivitypractice.databinding.ActivityMainBinding

const val CHANNEL_ID = "myChannel"
const val NOTIFICATION_ID = 100

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val channel = NotificationChannel(CHANNEL_ID, "General Notifications", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, permissions, 200)
        }

        val remoteViews = RemoteViews(packageName, R.layout.notification_layout)

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCustomBigContentView(remoteViews)
            .build()

        binding.showNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)

            Handler(Looper.getMainLooper()).postDelayed({
                remoteViews.setImageViewResource(R.id.stage_view, R.drawable.stage2)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }, 8000)

            Handler(Looper.getMainLooper()).postDelayed({
                remoteViews.setImageViewResource(R.id.stage_view, R.drawable.stage3)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }, 15000)
        }
    }
}