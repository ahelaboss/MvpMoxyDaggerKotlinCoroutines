package com.yourgains.mvpmoxydaggertemplate.data.repository

import com.yourgains.mvpmoxydaggertemplate.domain.repository.IProgressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepositoryImpl @Inject constructor() : IProgressRepository {

    private val listProcess: MutableList<Any> = mutableListOf()

    override fun showProgressBar(obj: Any) {
        listProcess.add(obj)
        takeIf { listProcess.isNotEmpty() }?.let {
//            progressStateEvent.value = StateEvent.SHOW
        }
    }

    override fun hideProgressBar(obj: Any) {
        listProcess.remove(obj)
        takeIf { listProcess.isEmpty() }?.let {
//            progressStateEvent.value = StateEvent.HIDE
        }
    }

//    override fun observeReceiveChannel(): Flowable<List<ItemDBModel>> {
//
//    }
}