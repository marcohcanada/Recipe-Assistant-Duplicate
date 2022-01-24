package com.jam.recipeassistant.api

import android.content.res.Resources
import android.provider.Settings.Global.getString
import com.jam.recipeassistant.R
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody




class SuggestionsAPI {

    private val client = OkHttpClient()

    /*var Base_URL : String = //Resources.getSystem().getString(R.string.Base_URL);
    var ApiSection: String = "/Suggestion";*/

    public fun getGeneralSuggestion(callback: (input : MutableList<RecipeCard>) -> Unit) {
        val request = Request.Builder().url("http://20.115.105.215/Suggestions/GetGeneralSuggestion" /*(Base_URL + ApiSection + "GetGeneralSuggestion")*/).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                callback((Json.decodeFromString<List<RecipeCard>>(response.body()!!.string())).toMutableList());

            }
        })
    }



    public fun GetUsersRecipesById(callback: (input : MutableList<RecipeCard>) -> Unit) {
        val formBody: RequestBody = FormBody.Builder()
            .add("message", "Your message")
            .build()
        val request: Request = Request.Builder()
            .url("https://www.example.com/index.php")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                callback((Json.decodeFromString<List<RecipeCard>>(response.body()!!.string())).toMutableList());

            }
        })
    }
}