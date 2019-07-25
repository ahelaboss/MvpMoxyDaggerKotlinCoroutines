package com.yourgains.mvpmoxydaggertemplate.data.entity.mappers

import com.yourgains.mvpmoxydaggertemplate.data.entity.db.ItemDBModel
import com.yourgains.mvpmoxydaggertemplate.data.entity.network.ItemResponse
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
object DbModelMapper {

    fun map(model: ItemResponse): ItemDBModel = ItemDBModel(model.id, model.name)

    fun map(model: ItemUIModel): ItemDBModel = ItemDBModel(model.id, model.name)
}