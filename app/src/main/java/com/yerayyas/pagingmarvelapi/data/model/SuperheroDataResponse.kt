package com.yerayyas.pagingmarvelapi.data.model

import com.google.gson.annotations.SerializedName
import com.yerayyas.pagingmarvelapi.data.model.Data

data class SuperheroDataResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)
