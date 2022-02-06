package com.jam.recipeassistant.api

import com.jam.recipeassistant.model.Login.Intolerances
import com.jam.recipeassistant.model.Login.UserLogin
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType
import java.io.BufferedReader
import java.io.File

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-30
 */
class LoginAPI {
    private val client = OkHttpClient()

    public fun VerifyLogin(content: UserLogin, callback: (input : UserLogin) -> Unit) {
        var JSON = "application/json; charset=utf-8".toMediaType()
        var jsoncontent = "{\"email\": \""+content.email+"\", \"password\": \""+content.password+"\", \"result\": "+content.result+"}"
        var body:RequestBody = RequestBody.create(JSON, jsoncontent);
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/UserManagement/CheckLogin")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<UserLogin>(body!!.string())));

            }
        })
    }

    public fun GetUserIntolerances(getFilesDirPath :String, callback: (input : MutableList<Intolerances>) -> Unit) {
        val bufferedReader: BufferedReader = File(getFilesDirPath + "/somefile.txt").bufferedReader()
        var JSON = "application/json; charset=utf-8".toMediaType()
        var email = bufferedReader.use { it.readText() }
        var jsoncontent = "{\"email\": \""+email+"\"}"
        var body:RequestBody = RequestBody.create(JSON, jsoncontent);
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/UserManagement/GetUserIntolerances")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
                println(e.stackTrace)
            }
            override fun onResponse(call: Call, response: Response) {
                val body:ResponseBody? = response.body
                callback((Json.decodeFromString<MutableList<Intolerances>>(body!!.string())));
            }
        })
    }

    public fun AddIntolerance(getFilesDirPath :String, content: Intolerances) {
        val bufferedReader: BufferedReader = File(getFilesDirPath + "/somefile.txt").bufferedReader()
        var JSON = "application/json; charset=utf-8".toMediaType()
        var email = bufferedReader.use { it.readText() }
        var jsoncontent = "{\"email\": \""+email+"\", \"ingredient\": \""+content.ingredient+"\", \"severity\": "+content.severity+"}"
        var body:RequestBody = RequestBody.create(JSON, jsoncontent);
        /*val formBody: RequestBody = FormBody.Builder()
            //.add("email", "adriangonzalezmadruga@gmail.com")
            .build()*/
        val request: Request = Request.Builder()
            .url("http://52.186.139.166/UserManagement/AddIntolerance")
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
}