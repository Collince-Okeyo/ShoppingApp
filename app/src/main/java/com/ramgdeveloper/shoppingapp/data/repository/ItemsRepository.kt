package com.ramgdeveloper.shoppingapp.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.ramgdeveloper.shoppingapp.data.local.ItemsDao
import com.ramgdeveloper.shoppingapp.model.ItemsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemsRepository @Inject constructor(private val itemsDao: ItemsDao) {
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("items")


    fun getAllItems(): Flow<List<ItemsEntity>> {
        return itemsDao.getAllItems()
    }

    suspend fun insertItems(items: ItemsEntity){
        return itemsDao.insertItems(items)
    }

    fun searchDatabase(searchQuery: String): Flow<List<ItemsEntity>> {
        return itemsDao.searchDatabase(searchQuery)
    }

    init {
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}