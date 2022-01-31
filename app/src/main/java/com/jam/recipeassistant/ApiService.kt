package com.jam.recipeassistant

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-15
 */

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Adding the base url of the API endpoint
private const val BASE_URL = ""

// Initializing the http logging client, moshi, and retrofit

// logging
private fun getHttpClient(): OkHttpClient {
    //val logging = HttpLoggingInterceptor()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)
    return httpClient.build()
}

// instantiating moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// instantiating retrofit

// Uncommenting the BASE url
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(getHttpClient())
    .build()