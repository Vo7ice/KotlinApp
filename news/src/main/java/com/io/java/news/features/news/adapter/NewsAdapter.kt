package com.io.java.news.features.news.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.io.java.news.bean.RedditNewsItem
import com.io.java.news.common.Constant
import com.io.java.news.common.ViewType
import com.io.java.news.common.ViewTypeDelegateAdapter

/**
 * Created by huguojin on 2018/3/27.
 */
class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val item: List<ViewType> = ArrayList()
    private val delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val mLoadingItem = object : ViewType {
        override fun getViewType() = Constant.LOADING
    }

    init {

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addNews(news: List<RedditNewsItem>) {

    }
}