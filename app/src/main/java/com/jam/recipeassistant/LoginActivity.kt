package com.jam.recipeassistant

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jam.recipeassistant.api.UserManagementAPI
import com.jam.recipeassistant.model.UserManagement.UserLogin
import okhttp3.*
import java.io.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    /*var channelId = "channel_id_example_01"
    val notificationId = 101*/

    val name = "Notification Title"
    val descriptionText = "Notification Description"

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
            UserManagementAPI().VerifyLogin(userLogin, fun(input: UserLogin) {
                if (input.result == 1) {
                    this.runOnUiThread(java.lang.Runnable {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        scheduleNotification()

                        File(getFilesDir().path + "/somefile.txt").writeText(userLogin.email)


                        /*val file = "abc.txt"
                        val data = "This is an example of internal storage in Android"
                        val fileOutputStream: FileOutputStream
                        try {
                            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                            fileOutputStream.write(data.toByteArray())
                        } catch (e: FileNotFoundException){
                            e.printStackTrace()
                        }catch (e: NumberFormatException){
                            e.printStackTrace()
                        }catch (e: IOException){
                            e.printStackTrace()
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                        val filename = name
                        var fileInputStream: FileInputStream? = null
                        fileInputStream = openFileInput(filename)
                        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                        val stringBuilder: StringBuilder = StringBuilder()
                        var text: String? = null
                        while ({ text = bufferedReader.readLine(); text }() != null) {
                            stringBuilder.append(text)
                        }*/
                        Toast.makeText(this@LoginActivity,
                            "LOGIN SUCCESSFUL AND DATA SAVED", Toast.LENGTH_LONG).show()
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
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification() {
        val intent = Intent(applicationContext, BroadcastReceiver::class.java)
        val title = name
        val message = descriptionText
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
    }

    fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.setTime(Date())
        calendar.add(Calendar.MINUTE, 2)
        return calendar.timeInMillis
    }
}