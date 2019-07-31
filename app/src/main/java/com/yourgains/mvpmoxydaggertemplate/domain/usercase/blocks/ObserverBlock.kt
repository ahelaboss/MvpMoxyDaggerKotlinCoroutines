package com.yourgains.mvpmoxydaggertemplate.domain.usercase.blocks

import com.yourgains.mvpmoxydaggertemplate.domain.usercase.BaseObserveCoroutinesUseCase

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */

typealias ObserverBlock<T> = BaseObserveCoroutinesUseCase.Request<T>.() -> Unit