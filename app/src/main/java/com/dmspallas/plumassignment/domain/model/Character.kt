package com.dmspallas.plumassignment.domain.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    val thumbnailExt : String
)