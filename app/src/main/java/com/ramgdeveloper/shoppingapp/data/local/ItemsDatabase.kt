package com.ramgdeveloper.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramgdeveloper.shoppingapp.model.ItemsEntity

@Database(
    entities = [ItemsEntity::class],
    version = 1
)
abstract class ItemsDatabase: RoomDatabase() {
    abstract fun getItemsDao(): ItemsDatabase
}