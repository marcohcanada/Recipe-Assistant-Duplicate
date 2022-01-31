package com.jam.recipeassistant

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jam.recipeassistant.api.LoginAPI
import com.jam.recipeassistant.model.Login.UserLogin
import com.jam.recipeassistant.model.LoginRequest
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import com.jam.recipeassistant.model.Suggestions.User
import com.squareup.moshi.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<TextView>(R.id.email)
        val password = findViewById<TextView>(R.id.password)
        val loginbtn = findViewById<Button>(R.id.loginbtn)

        var userLogin: UserLogin

        //admin and admin
        loginbtn.setOnClickListener {
            userLogin = UserLogin(email.text.toString(), password.text.toString(), -1)
            LoginAPI().VerifyLogin(userLogin, fun (input: UserLogin) {
                if (userLogin.Result == 1) {
                    activity?.runOnUiThread(java.lang.Runnable {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show()
                    })
                } else {
                    //Toast.makeText(this@LoginActivity, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show()
                }
            })
        }
}}
