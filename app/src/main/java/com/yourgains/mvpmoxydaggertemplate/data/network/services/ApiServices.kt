package com.yourgains.mvpmoxydaggertemplate.data.network.services

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.ItemResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
interface ApiServices {

    @GET("/en/web/good-radio-online/api-get-category")
    fun getListAsync(): Deferred<List<ItemResponse>>

}