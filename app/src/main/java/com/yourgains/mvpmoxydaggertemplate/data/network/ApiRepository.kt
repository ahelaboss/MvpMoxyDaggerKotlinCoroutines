package com.yourgains.mvpmoxydaggertemplate.data.network

import com.yourgains.mvpmoxydaggertemplate.data.entity.network.ItemResponse
import com.yourgains.mvpmoxydaggertemplate.data.network.services.ApiServices

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
class ApiRepository(private val apiServices: ApiServices): IApiRepository {

    override suspend fun getListItems(): List<ItemResponse> = apiServices.getListAsync().await()
}