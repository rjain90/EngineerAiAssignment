package com.rjain90.engineeraiassignment.data.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private val TAG = "Application Handler"

    private var apiInterface: ApiInterface? = null

    private val baseURL: String
        get() {
            return Constants.URL_API
        }

    fun initialize() {
        createRetrofitObject()
    }

    private fun createRetrofitObject() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getApiInterface(): ApiInterface? {
        if (apiInterface == null) {
            Log.i(TAG, "Api interface is null")
            createRetrofitObject()
        }
        return apiInterface
    }
}
