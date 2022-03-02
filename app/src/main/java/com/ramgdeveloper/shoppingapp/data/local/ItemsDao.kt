package com.ramgdeveloper.shoppingapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramgdeveloper.shoppingapp.model.ItemsEntity

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemsEntity>)

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<ItemsEntity>>

    @Query("DELETE FROM items")
    suspend fun deleteItems()

}