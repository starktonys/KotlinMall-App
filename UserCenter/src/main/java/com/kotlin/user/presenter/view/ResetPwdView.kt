package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView

/*
    重置密码 视图回调
 */
interface ResetPwdView : BaseView {

    fun onResetPwdResult(result:String)
}
