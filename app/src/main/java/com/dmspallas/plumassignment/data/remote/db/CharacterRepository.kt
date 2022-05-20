package com.dmspallas.plumassignment.data.remote.db

import javax.inject.Inject

class CharacterRepository @Inject constructor(private val dao: CharacterDao) {
    val characters = dao.getCharactersFromDb()


    suspend fun insert(characterEntity: CharacterEntity) {
        dao.insertCharacter(characterEntity)
    }

    suspend fun delete(name: String) {
        dao.deleteCharacter(name)
    }
}