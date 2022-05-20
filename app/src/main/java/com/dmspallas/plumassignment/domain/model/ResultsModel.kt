package com.dmspallas.plumassignment.domain.model

import com.google.gson.annotations.SerializedName

class ResultsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
) {
    fun toCharacter(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            description = description,
            thumbnail = thumbnail.path,
            thumbnailExt=thumbnail.extension,
        )
    }
}