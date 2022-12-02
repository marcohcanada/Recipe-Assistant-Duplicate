package com.jam.recipeassistant

import ai.api.model.GoogleAssistantResponseMessages
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

// Manager for Dialog Integration
class DialogflowManager: CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var fragment: Fragment? = null
    private var client: SessionsClient? = null
    private var session: SessionName? = null
    private var eventInput: EventInput? = null
    private var uuid: String = UUID.randomUUID().toString()
    private val LANGUAGE_CODE = "en"
    private val TAG = "DialogflowManager"

    fun initAssistant(context: Context, fragment: Fragment) {
        try {
            this.fragment = fragment
            val stream = context.resources.openRawResource(R.raw.credentials)
            val credentials = GoogleCredentials.fromStream(stream)
            val projectId = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder = SessionsSettings.newBuilder()
            val sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build()
            this.client = SessionsClient.create(sessionsSettings)!!
            this.session = (SessionName.of(projectId, uuid))!!
            this.eventInput = EventInput.newBuilder().setName("WELCOME").setLanguageCode(LANGUAGE_CODE).build()
            val response = this.client!!.detectIntent(this.session!!, QueryInput.newBuilder().setEvent(eventInput).build())
            val botMessage = GoogleAssistantResponseMessages.ResponseBasicCard()
            botMessage.formattedText = response.queryResult.fulfillmentText
            Log.d(TAG, "Recipe Assistant Response: ${botMessage.formattedText}")
            (fragment as ChatBotFragment).showTextView(botMessage.formattedText, 1002)
            Log.d(TAG, "projectId: $projectId")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    @Throws(Exception::class)
////    fun detectIntentText(payload: Int, text: String): QueryResult {
////
////        if(this.client == null || this.session == null){
////            throw Exception("Error: no dialogflow client")
////        }
////
////        // Set the text (input) and language code (en) for the query
//        val textInput = TextInput.newBuilder().setText(text).setLanguageCode(LANGUAGE_CODE)
//
//        // Build the query with the TextInput
//        this.queryInput = QueryInput.newBuilder().setText(textInput).build()!!
//
//        //Save received payload into a protobuf Struct
//        val currentPayload = Value.newBuilder().setNumberValue(payload.toDouble()).build()
//        val struct = Struct.newBuilder()
//        struct.putFields("currentPayload", currentPayload)
//
//        //Set queryParameters
//        val queryParameters = QueryParameters.newBuilder().setPayload(struct).build()
//
//        //Build the request
//        val request = DetectIntentRequest.newBuilder()
//            .setSession(this.session!!.toString())
//            .setQueryInput(this.queryInput)
//            .setQueryParams(queryParameters)
//            .build()
//
//        // Performs the detect intent request
//        val response = this.client!!.detectIntent(request)
//
//        // Display the query result
//        Log.d(TAG, response.queryResult.toString())
//        return response.queryResult
//    }

    fun onPause(){
        job.cancel()
    }

    private suspend fun doInBackground(queryInput: QueryInput) : DetectIntentResponse? = withContext(Dispatchers.IO) {
        try {
            val detectIntentRequest = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build()
            return@withContext client!!.detectIntent(detectIntentRequest)
        }
        catch (e: Exception){
            Log.d(TAG, "doInBackground: ${e.message}")
            return@withContext null
        }
    }

    fun execute(queryInput: QueryInput) = launch {
        val result = doInBackground(queryInput)
        onPostExecute(result!!)
    }

    private fun onPostExecute(response : DetectIntentResponse?) {
        callback(response)
        Fulfillment.newBuilder()
    }

    private fun callback(response : DetectIntentResponse?){
        if(response != null){
            val botMessage = GoogleAssistantResponseMessages.ResponseBasicCard()
            botMessage.formattedText = response.queryResult.fulfillmentText

            Log.d(TAG, "Recipe Assistant Response: ${botMessage.formattedText}")
            (fragment as ChatBotFragment).showTextView(botMessage.formattedText, 1002)
        }
        else{
            Log.d(TAG,"Recipe Assistant Response: No response")
            (fragment as ChatBotFragment).showTextView("No response from bot", 1002)
        }
    }
}