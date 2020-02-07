package com.yourgains.mvpmoxydaggertemplate.domain.usercase

abstract class BasePageCoroutinesUseCase<T> : BaseProgressCoroutinesUseCase<T>() {

    companion object {
        private const val START_PAGE = 1L
        private const val PER_PAGE = 20
    }

    var page = START_PAGE
    protected var total: Long = 0

    fun setData(page: Long) {
        this.page = page
    }

    fun increasePage(): Long = ++page

    fun decreasePage(): Long = takeIf { page == 1L }?.let { page } ?: --page

    fun resetPage() {
        page = START_PAGE
    }

    fun isFirstPage(): Boolean = page == START_PAGE

    fun isLastPage(): Boolean = page * getPerPage() >= total

    open fun getPerPage(): Int = PER_PAGE

}