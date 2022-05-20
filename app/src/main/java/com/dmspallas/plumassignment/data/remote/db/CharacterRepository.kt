package com.dmspallas.plumassignment.data.remote.db

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val dao: CharacterDao) {
    val characters: Flow<List<CharacterEntity>> = dao.getCharactersFromDb()

    suspend fun insert(characterEntity: CharacterEntity) {
        dao.insertCharacter(characterEntity)
    }

    suspend fun delete(name: String) {
        dao.deleteCharacter(name)
    }

    fun check(name: String): Boolean {
        return dao.getCharacterFromDb(name)
    }
}