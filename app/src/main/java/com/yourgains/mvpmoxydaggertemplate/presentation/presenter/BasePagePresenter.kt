package com.yourgains.mvpmoxydaggertemplate.presentation.presenter

import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BasePageCoroutinesUseCase
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBasePageMvpView

abstract class BasePagePresenter<T, V : IBasePageMvpView<T>> : BasePresenter<V>() {

    var isLastPage: Boolean = true
    var isLoading: Boolean = false

    protected open fun loadFirstPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        loadNextPage(userCase, isFirstPage = true)
    }

    protected fun loadNextPage(
        userCase: BasePageCoroutinesUseCase<List<T>>,
        isFirstPage: Boolean = false
    ) {
        if (!isFirstPage) userCase.increasePage() else userCase.resetPage()
        isLoading = true
        userCase.execute {
            onComplete {
                isLastPage = it.size < userCase.getPerPage()
                showResult(userCase.page, it)
            }
            onNetworkError { isLoading = false }
            onError { isLoading = false }
            onCancel { isLoading = false }
        }
    }

    protected fun loadPrevPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        isLoading = true
        userCase.decreasePage()
        userCase.execute {
            onComplete {
                showResult(userCase.page, it)
                isLastPage = false
            }
            onNetworkError { isLoading = false }
            onError { isLoading = false }
            onCancel { isLoading = false }
        }
    }

    private fun showResult(page: Long, list: List<T>){
        when (page) {
            1L -> viewState.initItems(list)
            else -> viewState.loadMoreItems(list)
        }
        if (list.isEmpty()) viewState.showEmptyState() else viewState.hideEmptyState()
        isLoading = false
    }
}