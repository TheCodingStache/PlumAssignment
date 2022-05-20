package com.dmspallas.plumassignment.domain.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("data")
    val data : String,
    @SerializedName("results")
    val results : List<ResultsModel>
) {
}