package com.jam.recipeassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.api.UserManagementAPI
import com.jam.recipeassistant.model.Suggestions.UpdatedRecipeDetails
import com.jam.recipeassistant.model.UserManagement.NewUser
import com.jam.recipeassistant.model.UserManagement.UserLogin
import java.io.File

class SignUpActivity : AppCompatActivity() {

    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var password: TextView
    lateinit var firstname: TextView
    lateinit var lastname: TextView
    lateinit var addressline: TextView
    lateinit var city: TextView
    lateinit var state: TextView
    lateinit var postalcode: TextView
    lateinit var country: TextView
    lateinit var createaccountbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        username = findViewById<TextView>(R.id.username)
        email = findViewById<TextView>(R.id.email)
        password = findViewById<TextView>(R.id.password)
        firstname = findViewById<TextView>(R.id.firstname)
        lastname = findViewById<TextView>(R.id.lastname)
        addressline = findViewById<TextView>(R.id.addressline)
        city = findViewById<TextView>(R.id.city)
        state = findViewById<TextView>(R.id.state)
        postalcode = findViewById<TextView>(R.id.postalcode)
        country = findViewById<TextView>(R.id.country)
        createaccountbtn = findViewById<Button>(R.id.createaccountbtn)

        var newUser: NewUser

        createaccountbtn.setOnClickListener {
            newUser = NewUser(username.text.toString(), email.text.toString(), password.text.toString(), firstname.text.toString(), lastname.text.toString(),
            addressline.text.toString(), city.text.toString(), state.text.toString(), postalcode.text.toString(), country.text.toString())

            UserManagementAPI().CreateNewUser(newUser)

            File(getFilesDir().path + "/somefile.txt").writeText(newUser.Email)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}