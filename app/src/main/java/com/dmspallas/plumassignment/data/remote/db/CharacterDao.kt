package com.dmspallas.plumassignment.data.remote.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Query("select * from character_table")
    fun getCharactersFromDb(): LiveData<List<CharacterEntity>>

    @Query("select character_name from character_table where character_name = :name")
    fun getCharacterFromDb(name: String): LiveData<String>

    @Query("delete from character_table where character_name = :name")
    suspend fun deleteCharacter(name : String)
}