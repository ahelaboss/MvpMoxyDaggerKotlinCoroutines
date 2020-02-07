package com.yourgains.mvpmoxydaggertemplate.domain.repository

interface IProgressRepository {

    fun showProgressBar(obj: Any)
    fun hideProgressBar(obj: Any)
//    fun observeReceiveChannel(): Flowable<List<ItemDBModel>>
}