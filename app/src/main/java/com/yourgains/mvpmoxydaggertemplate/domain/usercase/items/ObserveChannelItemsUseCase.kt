package com.yourgains.mvpmoxydaggertemplate.domain.usercase.items

import com.yourgains.mvpmoxydaggertemplate.data.entity.mappers.UiModelMapper
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseObserveCoroutinesUseCase
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.reactive.openSubscription
import javax.inject.Inject

/**
 * Created by Alexey Shishov
 * on 25.07.19
 */
class ObserveChannelItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
) : BaseObserveCoroutinesUseCase<List<ItemUIModel>>() {

    @ObsoleteCoroutinesApi
    override fun observe(): ReceiveChannel<List<ItemUIModel>> =
        itemsRepository.observeReceiveChannel().map { list -> list.map { UiModelMapper.map(it) } }.openSubscription()
}

