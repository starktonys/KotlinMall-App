package com.kotlin.base.utils

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.kotlin.base.ui.activity.BaseActivity

/**
 * created on: 2018/8/7 22:11
 * description:
 */
object ToolbarHelper {

    fun initTranslucent(activity: BaseActivity) {
        StatusBarUtil.setTranslucentStatus(activity)
        StatusBarUtil.setLightMode(activity)
    }

    /**
     * 将该View的 top Padding 向下偏移一个状态栏的高度
     */
    fun initPaddingTopDiffBar(view: View) {
        view.setPadding(
                view.paddingLeft,
                view.paddingTop + ScreenUtils.getStatusHeight(view.context),
                view.paddingRight,
                view.paddingBottom)
    }

    /**
     * 将View的top margin 向下偏移一个状态栏的高度
     */
    fun initMarginTopDiffBar(view: View) {
        val params = view.layoutParams
        if (params is LinearLayout.LayoutParams) {
            params.topMargin += ScreenUtils.getStatusHeight(view.context)
        } else if (params is FrameLayout.LayoutParams) {
            params.topMargin += ScreenUtils.getStatusHeight(view.context)
        } else if (params is RelativeLayout.LayoutParams) {
            params.topMargin += ScreenUtils.getStatusHeight(view.context)
        }
        //        else if (params instanceof ConstraintLayout.LayoutParams) {
        //            ConstraintLayout.LayoutParams constraintParams = (ConstraintLayout.LayoutParams) params;
        //            constraintParams.topMargin += ScreenUtils.Companion.getStatusHeight(view.getContext());
        //        }
        view.layoutParams = params
    }

    /**
     * 将Toolbar高度填充到状态栏
     */
    fun initFullBar(toolbar: Toolbar, activity: BaseActivity) {
        val params = toolbar.layoutParams
        params.height = ScreenUtils.getStatusHeight(activity) + ScreenUtils.getSystemActionBarSize(activity)
        toolbar.layoutParams = params
        toolbar.setPadding(
                toolbar.left,
                toolbar.top + ScreenUtils.getStatusHeight(activity),
                toolbar.right,
                toolbar.bottom
        )
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }
}
