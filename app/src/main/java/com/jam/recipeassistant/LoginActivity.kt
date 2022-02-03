package com.jam.recipeassistant

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jam.recipeassistant.api.LoginAPI
import com.jam.recipeassistant.model.Login.UserLogin
import okhttp3.*


class LoginActivity : AppCompatActivity() {

    var channelId = "channel_id_example_01"
    val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<TextView>(R.id.email)
        val password = findViewById<TextView>(R.id.password)
        val loginbtn = findViewById<Button>(R.id.loginbtn)

        email.setText("a@b.ca")
        password.setText("123")

        var userLogin: UserLogin

        createNotificationChannel()

        //admin and admin
        loginbtn.setOnClickListener {
            userLogin = UserLogin(email.text.toString(), password.text.toString(), -1)
            LoginAPI().VerifyLogin(userLogin, fun(input: UserLogin) {
                if (input.result == 1) {
                    this.runOnUiThread(java.lang.Runnable {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        sendNotification()
                        Toast.makeText(this@LoginActivity, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT)
                            .show()
                    })
                } else {
                    this.runOnUiThread(java.lang.Runnable {
                        Toast.makeText(this@LoginActivity, "LOGIN FAILED !!!", Toast.LENGTH_SHORT)
                            .show()
                    })
                }
            })
        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.send_icon)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.send)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example Title")
            .setContentText("Example Description")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line so we need to extend it to be much, much longer"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}
