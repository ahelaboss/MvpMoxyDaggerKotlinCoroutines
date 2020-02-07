package com.yourgains.mvpmoxydaggertemplate.domain.repository

import com.yourgains.mvpmoxydaggertemplate.data.entity.db.ItemDBModel
import io.reactivex.Flowable

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
interface IItemsRepository {

    suspend fun get(): List<ItemDBModel>

    suspend fun delete(model: ItemDBModel)

    suspend fun clear()

    fun observeReceiveChannel(): Flowable<List<ItemDBModel>>
}