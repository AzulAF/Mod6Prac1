package com.azul.mod6prac1.data

import com.azul.mod6prac1.data.db.ItemDao
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.data.network.ItemsApi
import com.azul.mod6prac1.data.network.model.ItemDetailDto
import com.azul.mod6prac1.data.network.model.ItemOutsideDto
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.Call

class ItemRepository (
    private val itemDao: ItemDao? = null,
    private val retrofit: Retrofit? = null

){
    private val itemsApi: ItemsApi? = retrofit?.create(ItemsApi::class.java)

    fun getItemsApiary(): Call<MutableList<ItemOutsideDto>>? = itemsApi?.getItemsApiary()

   fun getItemDetailApiary(id: String?): Call<ItemDetailDto>? = itemsApi?.getItemDetailApiary(id)

    suspend fun insertItem(item: ItemEntity){
        itemDao?.insertItem(item)
    }
    suspend fun getAllItems(): MutableList<ItemEntity>? = itemDao?.getAllItems()

    suspend fun updateItem(item: ItemEntity){
        itemDao?.updateItem(item)
    }
    suspend fun deleteItem(item: ItemEntity){
        itemDao?.deleteItem(item)
    }
}
