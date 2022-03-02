package com.ramgdeveloper.shoppingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val itemImage: String? = null,
    val itemName: String? = null,
    val itemOldPrice: String? = null,
    val itemPrice: String? = null
)