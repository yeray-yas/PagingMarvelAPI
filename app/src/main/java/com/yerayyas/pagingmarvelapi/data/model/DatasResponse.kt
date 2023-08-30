package com.yerayyas.pagingmarvelapi.data.model

import com.google.gson.annotations.SerializedName

data class DatasResponse(
    @SerializedName("results")
    val results: List<Result>
)