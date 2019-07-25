package com.yourgains.mvpmoxydaggertemplate.domain.usercase

import androidx.lifecycle.LiveData

/**
 * Created by Alexey Shishov
 * on 25.07.19
 */
abstract class BaseObserveUseCase<T> {

    abstract fun observe(): LiveData<T>
}