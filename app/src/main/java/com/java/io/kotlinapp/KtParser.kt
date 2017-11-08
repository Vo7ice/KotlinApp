package com.java.io.kotlinapp

import android.content.Context
import android.util.Log

/**
 * Created by huguojin on 2017/11/8.
 */
class KtParser {
    val TAG: String = "Vo7ice"

    var mXmlPullParser = null
    var mInputStream = null
    var mXmlPullParserFactory = null

    fun parse(context: Context): Unit {
        Log.d(TAG, "start parse")

    }

    class Category {
        var name: String? = null
        var mModuleList: List<Module> = ArrayList()

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("Category name:$name")
            mModuleList.forEach { module -> sb.append(module.toString()) }
            return sb.toString()
        }
    }

    class Module {
        var name: String? = null
        var mFunctionList: List<Function> = ArrayList()

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("Module name:$name")
            mFunctionList.forEach { function -> sb.append(function.toString()) }
            return sb.toString()
        }
    }

    class Function {
        var name: String? = null
        var mSetList: List<Set> = ArrayList()

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("Function name:$name")
            mSetList.forEach { set -> sb.append(set.toString()) }
            return sb.toString()
        }
    }

    class Set {
        var name: String? = null
        var mItemList: List<Item> = ArrayList()

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("Set name:$name")
            mItemList.forEach { item -> sb.append(item.toString()) }
            return sb.toString()
        }
    }

    class Item {
        var name: String? = null
        var value: String? = null

        override fun toString(): String {
            return "[Item value:$value,name:$name]"
        }
    }
}