package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.user.data.protocol.UserInfo

/*
    用户登录 视图回调
 */
interface LoginView : BaseView {

    fun onLoginResult(result:UserInfo)
}
