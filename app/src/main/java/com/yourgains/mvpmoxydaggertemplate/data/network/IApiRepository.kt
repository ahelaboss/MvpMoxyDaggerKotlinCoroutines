package com.yourgains.mvpmoxydaggertemplate.data.network

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.ItemResponse

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
interface IApiRepository {

    suspend fun getListItems(): List<ItemResponse>
}