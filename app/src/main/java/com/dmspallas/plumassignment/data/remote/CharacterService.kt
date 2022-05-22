package com.dmspallas.plumassignment.data.remote

import com.dmspallas.plumassignment.domain.model.Character
import com.dmspallas.plumassignment.domain.model.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.RuntimeException
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val api: MarvelAPI
) {
    suspend fun fetchCharacters(): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.success(api.fetchCharacters().data.results.map { results -> results.toCharacter() }))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}