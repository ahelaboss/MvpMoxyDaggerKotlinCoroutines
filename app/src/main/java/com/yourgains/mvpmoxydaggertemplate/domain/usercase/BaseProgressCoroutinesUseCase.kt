package com.yourgains.mvpmoxydaggertemplate.domain.usercase

import com.yourgains.mvpmoxydaggertemplate.domain.repository.IProgressRepository
import javax.inject.Inject

abstract class BaseProgressCoroutinesUseCase<T> : BaseCoroutinesUseCase<T>() {

    @Inject
    lateinit var progressRepository: IProgressRepository

    override fun executePreBackground() {
        progressRepository.showProgressBar(this)
    }

    override fun executePostBackground() {
        progressRepository.hideProgressBar(this)
    }
}