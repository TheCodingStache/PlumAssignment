package com.dmspallas.plumassignment.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {
    @GET("/v1/public/characters")
    suspend fun fetchCharacters(@Query("ts") ts: String = "1",
                                @Query("apikey") apiKey : String = "196a259924f9f33f886656632b2855e4",
                                @Query("hash") hash : String = "c2312de3afae7b58d6613302e5cd3d04"): CharactersDto
}