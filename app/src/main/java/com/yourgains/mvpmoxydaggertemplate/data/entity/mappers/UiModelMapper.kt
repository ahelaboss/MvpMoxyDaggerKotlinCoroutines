package com.yourgains.mvpmoxydaggertemplate.data.entity.mappers

import com.yourgains.mvpmoxydaggertemplate.data.entity.db.ItemDBModel
import com.yourgains.mvpmoxydaggertemplate.data.entity.network.ItemResponse
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
object UiModelMapper {

    fun map(model: ItemDBModel): ItemUIModel = ItemUIModel(model.id, model.name)
}