package com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main

import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.GetItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.ObserveChannelItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IFirstView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.BasePresenter
import kotlinx.coroutines.channels.actor
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject


@InjectViewState
class FirstPresenter @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val observeChannelItemUserCase: ObserveChannelItemsUseCase
) : BasePresenter<IFirstView>() {

    private val observeActor = actor<List<ItemUIModel>>(coroutineContext) {
        for (items in channel) {
            Timber.e("observeActor: %s", items.size.toString())
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        observeChannelItemUserCase.execute {
            onChange {
                Timber.e("onChange: %s", it.size)
            }
        }

        observeChannelItemUserCase.execute(observeActor)

        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it.size)
            }

            onNetworkError { Timber.e(it) }
            onError { Timber.e(it) }
        }
    }


    override fun onDestroy() {
        observeChannelItemUserCase.unsubscribe()
        super.onDestroy()
    }

    fun navigateToSecondScreen() {
        viewState.onNavigateToSecond()
    }
}