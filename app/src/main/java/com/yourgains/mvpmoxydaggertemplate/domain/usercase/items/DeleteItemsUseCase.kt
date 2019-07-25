package com.yourgains.mvpmoxydaggertemplate.domain.usercase.items

import com.yourgains.mvpmoxydaggertemplate.data.entity.mappers.DbModelMapper
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseCoroutinesUseCase
import javax.inject.Inject

/**
 * Created by Alexey Shishov
 * on 25.07.19
 */
class DeleteItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
) : BaseCoroutinesUseCase<Unit>() {

    private var model: ItemUIModel? = null

    override suspend fun executeOnBackground() {
        model?.let { itemsRepository.delete(DbModelMapper.map(it)) }
    }

    fun setData(model: ItemUIModel) {
        this.model = model
    }

}