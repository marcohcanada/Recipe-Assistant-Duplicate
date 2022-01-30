package com.jam.recipeassistant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email = findViewById<TextView>(R.id.email)
        val password = findViewById<TextView>(R.id.password)
        val loginbtn = findViewById<Button>(R.id.loginbtn)

        //admin and admin
        loginbtn.setOnClickListener {
            //correct
            if (email.text.toString() == "admin" && password.text.toString() == "admin") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@LoginActivity, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show()
            }
            //incorrect
            else {
                Toast.makeText(this@LoginActivity, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
