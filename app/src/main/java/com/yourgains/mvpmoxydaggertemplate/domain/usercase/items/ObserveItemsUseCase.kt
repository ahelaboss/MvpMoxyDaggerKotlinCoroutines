package com.yourgains.mvpmoxydaggertemplate.domain.usercase.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.yourgains.mvpmoxydaggertemplate.data.entity.mappers.UiModelMapper
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseObserveUseCase
import javax.inject.Inject

/**
 * Created by Alexey Shishov
 * on 25.07.19
 */
class ObserveItemsUseCase @Inject constructor(
    private val itemsRepository: IItemsRepository
) : BaseObserveUseCase<List<ItemUIModel>>() {

    override fun observe(): LiveData<List<ItemUIModel>> =
        Transformations.map(itemsRepository.observe()) { list -> list.map { UiModelMapper.map(it) } }

}