package com.yourgains.mvpmoxydaggertemplate.domain.usercase.items

import com.yourgains.mvpmoxydaggertemplate.data.entity.mappers.UiModelMapper
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseCoroutinesUseCase
import javax.inject.Inject

/**
 * Created by Alexey Shishov
 * on 25.07.19
 */
class GetItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
) : BaseCoroutinesUseCase<List<ItemUIModel>>() {

    override suspend fun executeOnBackground(): List<ItemUIModel> =
        itemsRepository.get().map { model -> UiModelMapper.map(model) }
}