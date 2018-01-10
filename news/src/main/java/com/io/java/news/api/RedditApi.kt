package com.io.java.news.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by huguojin on 2018/1/10.
 */
interface RedditApi {

    @GET("/top.json")
    fun getTop(@Query("after") after: String, @Query("limit") limit: Int): Call<RedditNewsResponse>
}