package com.yerayyas.pagingmarvelapi.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val superheroes: List<SuperheroItemResponse>,
    @SerializedName("total")
    val total: Int
)