package com.azul.mod6prac1.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azul.mod6prac1.util.Constants

@Entity(tableName = Constants.DATABASE_ITEM_TABLE)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id", defaultValue = 0.toString())
    var id: Long = 0,
    @ColumnInfo(name = "item_merch", defaultValue = "Desconocido")
    var merch: String,
    @ColumnInfo(name = "item_cost", defaultValue =  "Desconocido")
    var cost: String,
    @ColumnInfo(name = "item_table", defaultValue = "Desconocido")
    var table: String,
    @ColumnInfo(name = "item_tag", defaultValue = "Desconocido")
    var tag : String,
    @ColumnInfo(name = "item_name", defaultValue = "Desconocido")
    var name : String,
    @ColumnInfo(name = "item_type", defaultValue = "Desconocido")
    var type: String,
    @ColumnInfo(name = "item_merchPriority", defaultValue = "Desconocido")
    var merchPriority: String

    )
