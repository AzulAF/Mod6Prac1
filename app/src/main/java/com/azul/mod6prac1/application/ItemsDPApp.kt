package com.azul.mod6prac1.application

import android.app.Application
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.db.ItemDatabase

class ItemsDPApp: Application() {

    private val database by lazy{
        ItemDatabase.getDatabase(this@ItemsDPApp)
    }

    val repository by lazy {
        ItemRepository(database.itemDao())
    }
}