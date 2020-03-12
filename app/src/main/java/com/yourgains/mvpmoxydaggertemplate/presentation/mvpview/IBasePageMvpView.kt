package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview

import moxy.viewstate.strategy.alias.OneExecution

interface IBasePageMvpView<T>: IBaseMvpView {

    @OneExecution
    fun initItems(list: List<T>)

    @OneExecution
    fun loadMoreItems(list: List<T>)

    @OneExecution
    fun showEmptyState()

    @OneExecution
    fun hideEmptyState()

}