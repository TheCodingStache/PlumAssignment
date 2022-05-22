package com.dmspallas.plumassignment.domain.model.repository

import com.dmspallas.plumassignment.data.remote.CharacterService
import com.dmspallas.plumassignment.domain.model.Character
import com.dmspallas.plumassignment.domain.model.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val service: CharacterService
){
    suspend fun getCharacters() : Flow<Result<List<Character>>> = service.fetchCharacters()
}