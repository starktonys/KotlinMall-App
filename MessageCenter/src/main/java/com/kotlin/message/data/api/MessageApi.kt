package com.kotlin.message.data.api

import rx.Observable
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.message.data.protocol.Message
import retrofit2.http.POST

/*
    消息 接口
 */
interface MessageApi {

    /*
        获取消息列表
     */
    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}
