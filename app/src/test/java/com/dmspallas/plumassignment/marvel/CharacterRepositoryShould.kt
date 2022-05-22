package com.dmspallas.plumassignment.marvel

import com.dmspallas.plumassignment.BaseUnitTest
import com.dmspallas.plumassignment.data.remote.CharacterService
import com.dmspallas.plumassignment.domain.model.Character
import com.dmspallas.plumassignment.domain.model.Results
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CharacterRepositoryShould : BaseUnitTest() {
    private val service: CharacterService = mock()
    private val characters = mock<List<Character>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getCharactersFromService() = runTest {
        val repository = CharacterRepository(service)
        repository.getCharacters()
        verify(service, times(1)).fetchCharacters()
    }

    @Test
    fun emitCharactersFromService() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(characters, repository.getCharacters().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailCase()
        assertEquals(exception, repository.getCharacters().first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): CharacterRepository {
        whenever(service.fetchCharacters()).thenReturn(
            flow {
                emit(Result.success(characters))
            }
        )
        return CharacterRepository(service)
    }

    private suspend fun mockFailCase(): CharacterRepository {
        whenever(service.fetchCharacters()).thenReturn(
            flow {
                emit(Result.failure<List<Character>>(exception))
            }
        )
        return CharacterRepository(service)
    }
}