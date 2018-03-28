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

    private val items = ArrayList<ViewType>()
    private val delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val mLoadingItem = object : ViewType {
        override fun getViewType() = Constant.LOADING
    }

    init {
        delegateAdapters.put(Constant.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(Constant.NEWS, NewsDelegateAdapter())
        items.add(mLoadingItem)
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    fun addNews(news: List<RedditNewsItem>) {
        val beforSize = items.size -1
        items.removeAt(beforSize)
        notifyItemRemoved(beforSize)

        items.addAll(news)
        items.add(mLoadingItem)
        notifyItemRangeChanged(beforSize, items.size +1)
    }
}