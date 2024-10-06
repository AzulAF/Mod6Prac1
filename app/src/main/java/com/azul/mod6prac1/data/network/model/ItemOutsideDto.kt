package com.azul.mod6prac1.data.network.model

import com.google.gson.annotations.SerializedName

data class ItemOutsideDto(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("piso")
    var piso: String? = null,

    @SerializedName("mesa")
    var mesa: String? = null,

    @SerializedName("imagen")
    var imagen: String? = null,

    @SerializedName("nombre")
    var nombre: String? = null

)
