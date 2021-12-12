package com.jam.recipeassistant

import android.content.Context
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2beta1.*
import com.google.protobuf.Struct
import com.google.protobuf.Value

class DialogflowManager {
    private var client: SessionsClient? = null
    private var session: SessionName? = null
    private var queryInput: QueryInput? = null



    companion object {
        private const val LANGUAGE_CODE = "en"
    }

    fun initAssistant(context: Context) {
        try {
            val stream = context.resources.openRawResource(R.raw.airy_task_334604_DialogFlow)
            val credentials = GoogleCredentials.fromStream(stream)
            val projectId = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder = SessionsSettings.newBuilder()
            val sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build()
            this.client = SessionsClient.create(sessionsSettings)
            this.session = SessionName.of(projectId, "recipe-assistant")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(Exception::class)
    fun detectIntentText(payload: Int, text: String): QueryResult {

        if(client == null || session == null){
            throw Exception("Error: no dialogflow client")
        }

        // Set the text (input) and language code (en) for the query
        val textInput = TextInput.newBuilder().setText(text).setLanguageCode(LANGUAGE_CODE)

        // Build the query with the TextInput
        queryInput = QueryInput.newBuilder().setText(textInput).build()

        //Save received payload into a protobuf Struct
        val payload = Value.newBuilder().setNumberValue(payload.toDouble()).build()
        val struct = Struct.newBuilder()
        struct.putFields("somePayload", payload)

        //Set queryParameters
        val queryParameters = QueryParameters.newBuilder().setPayload(struct).build()

        //Build the request
        val request = DetectIntentRequest.newBuilder()
            .setSession(session.toString())
            .setQueryInput(queryInput)
            .setQueryParams(queryParameters)
            .build()

        // Performs the detect intent request
        val response = client!!.detectIntent(request)

        // Display the query result
        return response.queryResult
    }
}