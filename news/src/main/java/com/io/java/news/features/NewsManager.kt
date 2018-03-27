package com.io.java.news.features

import com.io.java.news.api.NewsApi
import com.io.java.news.api.RestApi
import com.io.java.news.bean.RedditNews
import com.io.java.news.bean.RedditNewsItem
import rx.Observable

/**
 * Created by huguojin on 2018/3/19.
 */
class NewsManager(private val api: NewsApi = RestApi()) {

    companion object Factory {
        fun create(): NewsManager = NewsManager()
    }

    fun getNews(after: String, limit: Int = 10): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val execute = api.getNews(after, limit).execute()// 访问网络
            val response = execute.body().data // 拿到数据
            if (execute.isSuccessful) { // 是否成功
                val items = response.children.map {
                    val item = it.data
                    RedditNewsItem(item.author, item.title, item.num_comments, item.created, item.thumbnail, item.url)
                }
                val news = RedditNews(response.after ?: "", response.before ?: "", items)
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(execute.message()))
            }
        }
    }
}