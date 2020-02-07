package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview

interface IBasePageMvpView<T>: IBaseMvpView {

    fun initItems(list: List<T>)
    fun loadMoreItems(list: List<T>)
    fun showEmptyState()
    fun hideEmptyState()

}