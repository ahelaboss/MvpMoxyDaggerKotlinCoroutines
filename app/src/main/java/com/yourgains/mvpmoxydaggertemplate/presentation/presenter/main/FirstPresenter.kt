package com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main

import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.GetItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.items.ObserveItemsUseCase
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IFirstView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.BasePresenter
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class FirstPresenter @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val observeItemsUseCase: ObserveItemsUseCase
) : BasePresenter<IFirstView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it.size)
            }

            onNetworkError { Timber.e(it) }
            onError { Timber.e(it) }
        }
    }

    fun navigateToSecondScreen() {
        viewState.onNavigateToSecond()
    }
}