package com.jam.recipeassistant.api

import com.jam.recipeassistant.model.Shared.SimpleStringList
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import java.io.BufferedReader
import java.io.File

class RecipeManagementAPI {

    private val client = OkHttpClient()

    public fun UnListRecipe(recipeName:String) {
        var JSON = "application/json; charset=utf-8".toMediaType()
        var jsoncontent = "{\"recipeName\": \""+recipeName+"\", \"email\":\"nothing\"}"
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

    public fun CreateNewRecipe(jsonString:String) {
        var JSON = "application/json; charset=utf-8".toMediaType()
        var jsoncontent = jsonString
        var body:RequestBody = RequestBody.create(JSON, jsoncontent)
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/CreateRecipe")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {

            }
        })
    }

    public fun addDisLike(getFilesDirPath :String, recipeName:String) {
        val bufferedReader: BufferedReader = File(getFilesDirPath + "/somefile.txt").bufferedReader()
        var JSON = "application/json; charset=utf-8".toMediaType()
        var email = bufferedReader.use { it.readText() }
        var body:RequestBody = RequestBody.create(JSON, "{\"recipeName\": \""+recipeName+"\", \"email\":\""+email+"\"}");
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/addDisLike")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {

            }
        })

    }

    public fun addLike(getFilesDirPath :String, recipeName:String) {
        val bufferedReader: BufferedReader = File(getFilesDirPath + "/somefile.txt").bufferedReader()
        var JSON = "application/json; charset=utf-8".toMediaType()
        var email = bufferedReader.use { it.readText() }
        var body:RequestBody = RequestBody.create(JSON, "{\"recipeName\": \""+recipeName+"\", \"email\":\""+email+"\"}");
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/addLike")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {

            }
        })

    }

    public fun addReview(getFilesDirPath :String, recipeName:String, rating:Double, review:String) {
        val bufferedReader: BufferedReader = File(getFilesDirPath + "/somefile.txt").bufferedReader()
        var JSON = "application/json; charset=utf-8".toMediaType()
        var email = bufferedReader.use { it.readText() }
        var body:RequestBody = RequestBody.create(JSON, "{\"RecipeName\": \""+recipeName+"\", \"Email\":\""+email+"\", \"Rating\":"+rating+", \"Review\":\""+review+"\"}");
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/AddRecipeReview")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {

            }
        })

    }

    public fun GetAllIngredientUnits( callback: (input : SimpleStringList) -> Unit) {
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/GetAllIngredientUnits")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<SimpleStringList>(body!!.string())));

            }
        })
    }

    public fun GetAllIngredientNames( callback: (input : SimpleStringList) -> Unit) {
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/RecipeManagement/GetAllIngredientNames")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<SimpleStringList>(body!!.string())));

            }
        })
    }

}