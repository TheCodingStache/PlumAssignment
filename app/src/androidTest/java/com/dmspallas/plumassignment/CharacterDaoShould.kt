package com.dmspallas.plumassignment

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmspallas.plumassignment.data.remote.db.CharacterDao
import com.dmspallas.plumassignment.data.remote.db.CharacterDatabase
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDaoShould {
    private lateinit var database: CharacterDatabase
    private lateinit var dao: CharacterDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CharacterDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCharacter() = runBlocking() {
        val character = CharacterEntity(
            0, "Iron Man",
            "Tony Stark is charismatic and eccentric, he is also very proud, but also very altruist and heroic. He did not have a good childhood, and this affects how he acts. Stark is very smart, and a very talented inventor. His technology is always very advanced, and Iron Man is his greatest piece of work.",
            "https://image.api.playstation.com/vulcan/img/rnd/202010/2716/EN4RmIEX4nyQfWv6Vzi2eQ4g.jpg"
        )
        dao.insertCharacter(character)
    }

    @Test
    fun deleteCharacter() = runBlocking {
        val character = "Iron Man"
        dao.deleteCharacter(character)
    }

    @Test
    fun existsByName(): Unit = runBlocking {
        val character = "Iron Man"
        dao.getCharacterFromDb(character)
    }

}