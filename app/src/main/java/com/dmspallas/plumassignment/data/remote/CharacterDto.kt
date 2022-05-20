package com.dmspallas.plumassignment.data.remote

import com.dmspallas.plumassignment.domain.model.DataModel

data class CharactersDto(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: DataModel,
    val etag: String,
    val status: String
)