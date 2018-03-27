package com.io.java.news.features.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.java.news.R
import com.io.java.news.base.BaseFragment
import com.io.java.news.base.inflate
import com.io.java.news.base.showNackbar
import com.io.java.news.bean.RedditNews
import com.io.java.news.features.NewsManager
import kotlinx.android.synthetic.main.fragment_news.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by huguojin on 2018/1/10.
 */
class NewsFragment : BaseFragment() {

    private var redditNews: RedditNews? = null

    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private val newsManager by lazy {
        NewsManager.create()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        news_list.apply {
            news_list.setHasFixedSize(true)
            news_list.layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()

            // create adapter
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            val redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            val news = redditNews.news
            // set data from old value
        } else {
            requestData()// from internet
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // save old value
        if (redditNews != null /**/) {
            outState?.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy())
        }
    }

    private fun requestData() {
        val subscriber = newsManager.getNews(redditNews?.after ?: "", 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ retrievedNews ->
                    redditNews = retrievedNews
                    // set data
                }, { error ->
                    showNackbar(news_list, error.message ?: "")
                })
        subscriptions?.add(subscriber)
    }

    fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }
}