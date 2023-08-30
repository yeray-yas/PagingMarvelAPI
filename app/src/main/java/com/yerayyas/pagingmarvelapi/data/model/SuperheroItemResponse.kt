package com.yerayyas.pagingmarvelapi.data.model

import com.google.gson.annotations.SerializedName

data class SuperheroItemResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val superheroId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
)