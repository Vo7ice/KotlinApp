package com.io.java.news.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by huguojin on 2018/3/27.
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder?, item: ViewType)
}