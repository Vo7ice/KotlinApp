package com.io.java.news.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.io.java.news.R
import com.io.java.news.base.inflate
import com.io.java.news.common.ViewType
import com.io.java.news.common.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by huguojin on 2018/3/27.
 */
class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, item: ViewType) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    class TurnsViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.news_item)) {
        private val img_thumb = itemView.img_thumb
        private val txt_description = itemView.txt_description
    }
}