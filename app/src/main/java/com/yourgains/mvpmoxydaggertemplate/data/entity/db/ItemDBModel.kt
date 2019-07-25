package com.yourgains.mvpmoxydaggertemplate.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
@Entity(tableName = "items")
data class ItemDBModel(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String
)