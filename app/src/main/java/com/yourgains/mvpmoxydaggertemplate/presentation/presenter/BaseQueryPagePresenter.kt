package com.yourgains.mvpmoxydaggertemplate.presentation.presenter

import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BasePageCoroutinesUseCase
import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseQueryPageCoroutinesUseCase
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBaseQueryPageMvpView

abstract class BaseQueryPagePresenter<T, V : IBaseQueryPageMvpView<T>> : BasePagePresenter<T, V>() {

    companion object {
        const val MIN_CHARS = 3
        const val DELAY = 500L
    }

    protected var query: String? = null

    protected fun loadQueryFirstPage(
        query: String,
        useCase: BaseQueryPageCoroutinesUseCase<List<T>>
    ) {
        this.query = query
        useCase.setDelay(DELAY)
        useCase.setData(query)
        super.loadFirstPage(useCase)
    }

    override fun loadFirstPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        query ?: let { super.loadFirstPage(userCase) }
    }
}