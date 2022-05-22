package com.dmspallas.plumassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val data : String,
    @SerializedName("results")
    val results : List<Results>
) {
}