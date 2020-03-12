package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main

import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBasePageMvpView
import moxy.viewstate.strategy.alias.OneExecution

interface IFirstView: IBasePageMvpView<ItemUIModel> {

    @OneExecution
    fun onNavigateToSecond()
}