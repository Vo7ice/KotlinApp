package com.io.java.news.api

import retrofit2.Call

/**
 * Created by huguojin on 2018/1/10.
 */
interface NewsApi {
    fun getNews(after: String, limit: Int): Call<RedditNewsResponse>
}