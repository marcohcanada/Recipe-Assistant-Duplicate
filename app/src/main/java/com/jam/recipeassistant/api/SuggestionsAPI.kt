package com.jam.recipeassistant.api

import android.content.res.Resources
import android.provider.Settings.Global.getString
import com.jam.recipeassistant.R
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import com.jam.recipeassistant.model.Suggestions.User
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType

class SuggestionsAPI {

    private val client = OkHttpClient()

    public fun getGeneralSuggestion(callback: (input : MutableList<RecipeCard>) -> Unit) {
        val request = Request.Builder().url("http://52.186.139.166/Suggestions/GetGeneralSuggestion" /*(Base_URL + ApiSection + "GetGeneralSuggestion")*/).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<List<RecipeCard>>(body!!.string())).toMutableList());

            }
        })
    }

    public fun GetUsersRecipesByUser(callback: (input : MutableList<RecipeCard>) -> Unit) {
            var JSON = "application/json; charset=utf-8".toMediaType()
        var body:RequestBody = RequestBody.create(JSON, "{\"email\": \"adriangonzalezmadruga@gmail.com\"}");
        /*val formBody: RequestBody = FormBody.Builder()
            //.add("email", "adriangonzalezmadruga@gmail.com")
            .build()*/
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/Suggestions/GetUsersRecipesById")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<List<RecipeCard>>(body!!.string())).toMutableList());

            }
        })
    }
}