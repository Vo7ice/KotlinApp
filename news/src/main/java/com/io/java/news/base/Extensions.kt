package com.io.java.news.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by huguojin on 2018/1/10.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}