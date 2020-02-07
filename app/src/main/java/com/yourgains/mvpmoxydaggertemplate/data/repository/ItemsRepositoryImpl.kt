package com.yourgains.mvpmoxydaggertemplate.data.repository

import com.yourgains.mvpmoxydaggertemplate.data.entity.db.ItemDBModel
import com.yourgains.mvpmoxydaggertemplate.data.entity.mappers.DbModelMapper
import com.yourgains.mvpmoxydaggertemplate.data.network.services.APIServices
import com.yourgains.mvpmoxydaggertemplate.data.storage.database.dao.IItemsDao
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
class ItemsRepositoryImpl @Inject constructor(
    private val apiServices: APIServices,
    private val itemsDao: IItemsDao
) : IItemsRepository {

    override suspend fun get(): List<ItemDBModel> {
        val listItems = apiServices.getListAsync().await()
        itemsDao.insert(listItems.map { model -> DbModelMapper.map(model) })
        return itemsDao.select()
    }

    override suspend fun delete(model: ItemDBModel) {
        itemsDao.delete(model)
    }

    override suspend fun clear() {
        itemsDao.clear()
    }

    override fun observeReceiveChannel(): Flowable<List<ItemDBModel>> = itemsDao.observeCoroutine()
}