package com.io.java.news.base

import android.support.v4.app.Fragment
import rx.subscriptions.CompositeSubscription

/**
 * Created by huguojin on 2018/1/10.
 */
open class BaseFragment : Fragment() {

    protected var subscriptions: CompositeSubscription? = null
    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions?.clear()
    }
}