package com.yourgains.mvpmoxydaggertemplate.di.modules

import com.yourgains.mvpmoxydaggertemplate.data.repository.ItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsItemsRepository(repository: ItemsRepository): IItemsRepository
}