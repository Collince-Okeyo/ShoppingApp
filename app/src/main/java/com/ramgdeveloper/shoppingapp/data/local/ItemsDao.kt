package com.ramgdeveloper.shoppingapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramgdeveloper.shoppingapp.model.ItemsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: ItemsEntity)

    @Query("SELECT * FROM items ORDER BY id ASC ")
    fun getAllItems(): Flow<List<ItemsEntity>>

    @Query("SELECT * FROM items WHERE itemName LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ItemsEntity>>

}