package com.jam.recipeassistant.api

import com.jam.recipeassistant.model.Login.UserLogin
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType

class RecipeManagementAPI {

    private val client = OkHttpClient()

    public fun UnListRecipe(recipeName:String) {
        var JSON = "application/json; charset=utf-8".toMediaType()
        var jsoncontent = "{\"recipeName\": \""+recipeName+"\"}"
        var body:RequestBody = RequestBody.create(JSON, jsoncontent)
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/UnListRecipe")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                //val body:ResponseBody? = response.body
                //callback((Json.decodeFromString<UserLogin>(body!!.string())));

            }
        })
    }

}