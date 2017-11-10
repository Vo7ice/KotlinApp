package com.java.io.kotlinapp

import android.content.Context
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by huguojin on 2017/11/8.
 */
class KtParser {
    val TAG: String = "Vo7ice"

    var mInputStream = null
    var mXmlPullParserFactory = null
    lateinit var file:File

    fun parse(context: Context): Unit {
        Log.d(TAG, "start parse")
        var categorys: List<Category>? = null
        var categoryName: String? = null
        var category: Category? = null
        var module: Module? = null
        var moduleName: String? = null
        var function: Function? = null
        var funtionName: String? = null
        var set: Set? = null
        var setName: String? = null
        var item: Item? = null
        var itemName: String? = null
        var itemValue: String? = null


        try {
            val mXmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
            file = File("/sdcard/settings.xml")
            Log.d(TAG, "isExists:${file.exists()}")
            val mInputStream = FileInputStream(file)
            mXmlPullParser.setInput(mInputStream, null)
            var eventType = mXmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when(eventType) {
                    XmlPullParser.START_DOCUMENT -> {
                        categorys = ArrayList()
                    }
                    XmlPullParser.START_TAG -> {
                    }
                    XmlPullParser.END_TAG -> {
                    }
                }
                eventType = mXmlPullParser.next()
            }

        } catch (e: IOException) {

        }


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
