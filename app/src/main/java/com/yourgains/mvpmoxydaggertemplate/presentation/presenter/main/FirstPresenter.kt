package com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main

import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.GetItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.ObserveChannelItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IFirstView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.BasePagePresenter
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject


@InjectViewState
class FirstPresenter @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val observeChannelItemUserCase: ObserveChannelItemsUseCase
) : BasePagePresenter<ItemUIModel, IFirstView>() {

    @ObsoleteCoroutinesApi
    private val observeActor = actor<List<ItemUIModel>>(coroutineContext) {
        for (items in channel) {
            Timber.e("observeActor: %s", items.size.toString())
        }
    }

    @ObsoleteCoroutinesApi
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        observeChannelItemUserCase.execute {
            onChange {
                Timber.e("onChange: %s", it.size)
            }
        }
        observeChannelItemUserCase.execute(observeActor)

        loadFirstPage(getItemsUseCase)
    }

    override fun onDestroy() {
        observeChannelItemUserCase.unsubscribe()
        super.onDestroy()
    }

    fun navigateToSecondScreen() {
        viewState.onNavigateToSecond()
    }
}