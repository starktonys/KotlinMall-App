/*
 *    Copyright 2018 XuJiaji
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kotlin.base.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager

import java.lang.reflect.Method

/**
 * 屏幕获取相关的辅助类
 */
class ScreenUtils private constructor() {

    init {
        /*不能实例*/
        throw UnsupportedOperationException("不能被实例化")
    }

    companion object {
        private val TAG = "ScreenUtils"

        fun getSystemActionBarSize(context: Context): Int {
            val tv = TypedValue()
            return if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
            } else {
                dipTopx(context, 48f)
            }
        }

        /**
         * 获取屏幕宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        /**
         * 获取屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        /**
         * 获取状态栏的高度
         *
         * @param context
         * @return
         */
        fun getStatusHeight(context: Context): Int {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(`object`).toString())
                statusHeight = context.applicationContext.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

        /**
         * dp --> px
         *
         * @param context
         * @param dipValue
         * @return
         */
        fun dipTopx(context: Context, dipValue: Float): Int {
            val scale = context.applicationContext.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        /**
         * px --> dp
         *
         * @param context
         * @param pxValue
         * @return
         */
        fun pxTodip(context: Context, pxValue: Float): Int {
            val scale = context.applicationContext.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun scale(context: Context): Float {
            return context.applicationContext.resources.displayMetrics.density
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.applicationContext.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun px2sp(context: Context, px: Float): Float {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return px / scaledDensity + 0.5f
        }

        fun getFontSize(context: Context, textSize: Int): Int {
            val dm = DisplayMetrics()
            val windowManager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(dm)
            val screenHeight = dm.heightPixels
            val rate = (textSize * screenHeight.toFloat() / 1280).toInt()
            return rate
        }

        /**
         * 获取NavigationBar的高度
         */
        fun getNavigationBarHeight(context: Context): Int {
            var navigationBarHeight = 0
            val rs = context.applicationContext.resources
            val id = rs.getIdentifier("navigation_bar_height", "dimen", "android")
            if (id > 0 && checkDeviceHasNavigationBar(context)) {
                navigationBarHeight = rs.getDimensionPixelSize(id)
            }
            return navigationBarHeight
        }

        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.applicationContext.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {
                Log.w(TAG, e)
            }

            return hasNavigationBar

        }
    }
}
