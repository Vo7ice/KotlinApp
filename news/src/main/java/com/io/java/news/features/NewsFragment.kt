package com.io.java.news.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.java.news.R
import com.io.java.news.base.BaseFragment
import com.io.java.news.base.inflate
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * Created by huguojin on 2018/1/10.
 */
class NewsFragment : BaseFragment() {

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
        news_list.apply {  }
    }
}