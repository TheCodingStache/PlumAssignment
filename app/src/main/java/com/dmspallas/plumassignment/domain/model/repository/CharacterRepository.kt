package com.dmspallas.plumassignment.domain.model.repository

import com.dmspallas.plumassignment.data.remote.repository.CharacterService
import com.dmspallas.plumassignment.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: CharacterService
){
    suspend fun getCharacters() : Flow<Result<List<CharacterModel>>> = service.fetchCharacters()
}