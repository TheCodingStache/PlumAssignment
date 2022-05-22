package com.dmspallas.plumassignment.data.remote

import com.dmspallas.plumassignment.domain.model.Data

data class CharactersDto(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
){

}
