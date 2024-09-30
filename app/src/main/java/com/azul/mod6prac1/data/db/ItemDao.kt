package com.azul.mod6prac1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.util.Constants

@Dao
interface ItemDao {

    @Insert
    suspend fun  insertItem(item: ItemEntity)

    @Insert
    suspend fun insertItems(items: MutableList<ItemEntity>)

    @Query("SELECT * FROM ${Constants.DATABASE_ITEM_TABLE}")
    suspend fun getAllItems(): MutableList<ItemEntity>

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)
    
}