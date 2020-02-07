package com.yourgains.mvpmoxydaggertemplate.di.modules

import com.yourgains.mvpmoxydaggertemplate.data.repository.AuthRepositoryImpl
import com.yourgains.mvpmoxydaggertemplate.data.repository.ItemsRepositoryImpl
import com.yourgains.mvpmoxydaggertemplate.data.repository.ProgressRepositoryImpl
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IAuthRepository
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IItemsRepository
import com.yourgains.mvpmoxydaggertemplate.domain.repository.IProgressRepository
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
    abstract fun bindsItemsRepository(repository: ItemsRepositoryImpl): IItemsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): IAuthRepository

    @Binds
    @Singleton
    abstract fun bindProgressRepository(repository: ProgressRepositoryImpl): IProgressRepository
}