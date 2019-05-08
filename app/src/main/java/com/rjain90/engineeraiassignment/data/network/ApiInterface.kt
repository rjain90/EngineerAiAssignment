package com.rjain90.engineeraiassignment.data.network


import com.rjain90.engineeraiassignment.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search_by_date?tags=story")
    fun getPosts(@Query("page") page: Int): Call<PostResponse>
}
