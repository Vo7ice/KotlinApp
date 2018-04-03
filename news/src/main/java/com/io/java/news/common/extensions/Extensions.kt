package com.io.java.news.common.extensions

import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.io.java.news.R
import com.squareup.picasso.Picasso

/**
 * Created by huguojin on 2018/1/10.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun ImageView.loadImg(url: String) {
    if (TextUtils.isEmpty(url)) {
        Picasso.with(context).load(R.drawable.ic_account_circle_black_24dp).centerCrop().into(this)
    } else {
        Picasso.with(context).load(url).centerCrop().into(this)
    }
}