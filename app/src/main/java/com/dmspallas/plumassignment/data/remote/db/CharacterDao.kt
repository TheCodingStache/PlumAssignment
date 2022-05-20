package com.dmspallas.plumassignment.data.remote.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM character_table")
    fun getCharactersFromDb(): Flow<List<CharacterEntity>>

    @Query("SELECT EXISTS(SELECT * FROM character_table WHERE character_name == :name)")
    fun getCharacterFromDb(name: String) : Boolean

    @Query("DELETE FROM character_table WHERE character_name = :name")
    suspend fun deleteCharacter(name : String)
}