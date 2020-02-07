package com.yourgains.mvpmoxydaggertemplate.domain.usercase

abstract class BaseQueryPageCoroutinesUseCase<T> : BasePageCoroutinesUseCase<T>() {

    var query: String? = null

    fun setData(query: String) {
        this.query = query
    }

}