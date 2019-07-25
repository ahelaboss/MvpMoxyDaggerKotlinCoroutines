package com.yourgains.mvpmoxydaggertemplate.di.modules

import android.app.Application
import androidx.room.Room
import com.yourgains.mvpmoxydaggertemplate.data.storage.database.AppDatabase
import com.yourgains.mvpmoxydaggertemplate.data.storage.database.dao.IItemsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase = Room
        .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
        .addMigrations()
        .build()

    @Provides
    fun provideItemDao(db: AppDatabase): IItemsDao = db.itemDao()
}