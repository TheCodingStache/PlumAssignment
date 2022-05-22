package com.dmspallas.plumassignment.marvel

import com.dmspallas.plumassignment.BaseUnitTest
import com.dmspallas.plumassignment.data.remote.CharacterService
import com.dmspallas.plumassignment.data.remote.CharactersDto
import com.dmspallas.plumassignment.data.remote.MarvelAPI
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.lang.RuntimeException

class CharacterServiceShould : BaseUnitTest() {
    private lateinit var service: CharacterService
    private val api: MarvelAPI = mock()
    private val characters: CharactersDto = mock()
    @Test
    fun fetchCharactersFromAPI() = runTest {
        service = CharacterService(api)
        service.fetchCharacters().first()
        verify(api, times(1)).fetchCharacters()
    }


    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        whenever(api.fetchCharacters()).thenThrow(RuntimeException("Damn backend developers"))
        service = CharacterService(api)
        assertEquals(
            "Something went wrong",
            service.fetchCharacters().first().exceptionOrNull()?.message
        )
    }
}