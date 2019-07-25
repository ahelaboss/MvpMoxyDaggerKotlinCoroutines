package com.yourgains.mvpmoxydaggertemplate.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourgains.mvpmoxydaggertemplate.data.entity.db.ItemDBModel
import com.yourgains.mvpmoxydaggertemplate.data.storage.database.dao.IItemsDao

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
@Database(
    version = AppDatabase.VERSION,
    exportSchema = false,
    entities = [ItemDBModel::class]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "project_db_name.db" //TODO: Change db name
        const val VERSION = 1
    }

    abstract fun itemDao(): IItemsDao
}