package com.azul.mod6prac1.data

import com.azul.mod6prac1.data.db.ItemDao
import com.azul.mod6prac1.data.db.model.ItemEntity

class ItemRepository (private val itemDao: ItemDao){
    suspend fun insertItem(item: ItemEntity){
        itemDao.insertItem(item)
    }
    suspend fun getAllItems(): MutableList<ItemEntity> = itemDao.getAllItems()

    suspend fun updateItem(item: ItemEntity){
        itemDao.updateItem(item)
    }
    suspend fun deleteItem(item: ItemEntity){
        itemDao.deleteItem(item)
    }
}