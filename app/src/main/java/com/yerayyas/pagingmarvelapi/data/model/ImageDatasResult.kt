package com.yerayyas.pagingmarvelapi.data.model

import com.google.gson.annotations.SerializedName

data class ImageDatasResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DatasResponse,
    @SerializedName("status")
    val status: String
)