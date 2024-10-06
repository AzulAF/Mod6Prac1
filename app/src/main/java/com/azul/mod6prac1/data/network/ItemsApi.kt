package com.azul.mod6prac1.data.network

import com.azul.mod6prac1.data.network.model.ItemDetailDto
import com.azul.mod6prac1.data.network.model.ItemOutsideDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ItemsApi {

    @GET
    fun getItems(
        @Url url: String?
    ): Call <MutableList<ItemOutsideDto>>

    @GET("event/tables")
    fun getItemsApiary(): Call<MutableList<ItemOutsideDto>>

    @GET("event/tables/{id}")
    fun getItemDetailApiary(
        @Path("id") id:String?
    ): Call<ItemDetailDto>

}