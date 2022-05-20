package com.dmspallas.plumassignment.data.remote.repository

import com.dmspallas.plumassignment.data.remote.MarvelAPI
import com.dmspallas.plumassignment.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.RuntimeException
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val api: MarvelAPI
) {
    suspend fun fetchCharacters() : Flow<Result<List<CharacterModel>>> = flow {
        try {
            val marvel = api.fetchCharacters().data.results.map {
                it.toCharacter()
            }
            emit(Result.success(marvel))
        } catch (e: HttpException) {
            emit(
                Result.failure(
                    RuntimeException("Oops, something went completely wrong")
                )
            )
        }
    }
}