package com.io.java.news.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.io.java.news.R
import com.io.java.news.bean.RedditNewsItem
import com.io.java.news.common.ViewType
import com.io.java.news.common.ViewTypeDelegateAdapter
import com.io.java.news.common.extensions.getHumanRead
import com.io.java.news.common.extensions.inflate
import com.io.java.news.common.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by huguojin on 2018/3/27.
 */
class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    class TurnsViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.news_item)) {
        private val img_thumb = itemView.img_thumb
        private val txt_description = itemView.txt_description
        private val txt_author = itemView.txt_author
        private val txt_comments = itemView.txt_comments
        private val txt_time = itemView.txt_time

        fun bind(item: RedditNewsItem) {
            txt_description.text = item.title
            txt_author.text = item.author
            txt_comments.text = itemView.context.getString(R.string.txt_commenst, item.numComments)
            txt_time.text = item.created_utc.getHumanRead(itemView.context)
            img_thumb.loadImg(item.thumbnail)
        }
    }
}