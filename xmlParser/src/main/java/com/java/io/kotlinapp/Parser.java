package com.java.io.kotlinapp;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huguojin
 * @date 2017/11/8
 */

public class Parser {
    private static final String TAG = "Vo7ice";

    private XmlPullParser mXmlPullParser;
    private FileInputStream mInputStream;
    private XmlPullParserFactory mXmlPullParserFactory;

    public void parse(Context context) {
        Log.d(TAG, "start parse");

        List<Category> categoryList = null;
        Category category = null;
        String categoryName = "";
        Module module = null;
        String moduleName = "";
        Function function = null;
        String functionName = "";
        Set set = null;
        String setName = "";
        Item item = null;
        String value;
        String itemName;

        List<Item> itemList = new ArrayList<>();

        State mState;

        mState = State.IN;
        int ordinal = mState.ordinal();

        try {
            mXmlPullParserFactory = XmlPullParserFactory.newInstance();
            mXmlPullParser = mXmlPullParserFactory.newPullParser();
            File file = new File("/sdcard/settings.xml");
            Log.d(TAG, "isExsit:" + file.exists());
            mInputStream = new FileInputStream(file);
            mXmlPullParser.setInput(mInputStream, null);
            int eventType = mXmlPullParser.getEventType();
            Log.d(TAG, "eventType:" + eventType + "end:" + XmlPullParser.END_DOCUMENT);
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = mXmlPullParser.getName();
                Log.d(TAG, "tagName:" + tagName + ",eventType:" + eventType);
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        categoryList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "START_TAG -- tagName:" + tagName);
                        if ("category".equals(tagName)) {
                            categoryName = mXmlPullParser.getAttributeValue(null, "name");
                            Log.d(TAG, "categoryname:" + categoryName);
                            category = new Category();
                            category.name = categoryName;
                        }
                        if ("module".equals(tagName)) {
                            module = new Module();
                            moduleName = mXmlPullParser.getAttributeValue(null, "name");
                            Log.d(TAG, "modulename:" + moduleName);
                            module.name = moduleName;
                        }
                        if ("function".equals(tagName)) {
                            function = new Function();
                            functionName = mXmlPullParser.getAttributeValue(null, "name");
                            Log.d(TAG, "functionName:" + functionName);
                            function.name = functionName;
                        }
                        if ("set".equals(tagName)) {
                            set = new Set();
                            setName = mXmlPullParser.getAttributeValue(null, "name");
                            Log.d(TAG, "setName:" + setName);
                            set.name = setName;
                        }
                        if ("item".equals(tagName)) {
                            item = new Item();
                            itemName = mXmlPullParser.getAttributeValue(null, "name");
                            value = mXmlPullParser.nextText();
                            Log.d(TAG, "itemValue:" + value);
                            item.value = value;
                            item.name = itemName;
                            item.setName = setName;
                            item.functionName = functionName;
                            item.moduleName = moduleName;
                            item.categoryName = categoryName;
                            set.mItemList.add(item);
                            itemList.add(item);
                            item = null;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "END_TAG -- tagName:" + tagName);
                        if ("category".equals(tagName)) {
                            categoryList.add(category);
                            category = null;
                        }

                        if ("module".equals(tagName)) {
                            category.mModuleList.add(module);
                            module = null;
                        }

                        if ("function".equals(tagName)) {
                            module.mFunctionList.add(function);
                            function = null;
                        }

                        if ("set".equals(tagName)) {
                            function.mSetList.add(set);
                            set = null;
                        }

                    default:
                        break;
                }
                eventType = mXmlPullParser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "ioException");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d(TAG, "XmlPullParserException");
        } finally {
            StringBuilder sb = new StringBuilder();
            categoryList.forEach(ca -> sb.append(ca.toString()));
            Log.d(TAG, "category:" + sb.toString());
        }
    }

    public void test(Context context, String pkgName) throws ClassNotFoundException {

        ClassLoader loader = context.getClassLoader();
        Class<?> clazz = Class.forName(pkgName, true, loader);
    }

    class Category {
        String name;
        List<Module> mModuleList = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Category name:" + name + "]");
            mModuleList.forEach(module -> sb.append(module.toString()));
            return sb.toString();
        }
    }

    class Module {
        String name;
        List<Function> mFunctionList = new ArrayList<>();
        String categoryName;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Module name:" + name + "]");
            mFunctionList.forEach(function -> sb.append(function.toString()));
            return sb.toString();
        }
    }

    class Function {
        String name;
        List<Set> mSetList = new ArrayList<>();
        String moduleName;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Function name:" + name + "]");
            mSetList.forEach(set -> sb.append(set.toString()));
            return sb.toString();
        }

    }

    class Set {
        String name;
        List<Item> mItemList = new ArrayList<>();
        String functionName;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Set name:" + name + "]");
            mItemList.forEach(item -> sb.append(item.toString()));
            return sb.toString();
        }
    }

    class Item {
        String value;
        String name;
        String setName;
        String functionName;
        String moduleName;
        String categoryName;

        @Override
        public String toString() {
            return "[value:" + value + ",name:" + name + "]";
        }

        public void saveToDb() {

        }

    }

    public enum State {
        DIS, CONNECT, IN, OUT, INANDOUT
    }
}
