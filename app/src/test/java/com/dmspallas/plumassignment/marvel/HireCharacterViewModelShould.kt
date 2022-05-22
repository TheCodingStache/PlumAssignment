package com.dmspallas.plumassignment.marvel

import com.dmspallas.plumassignment.BaseUnitTest
import com.dmspallas.plumassignment.domain.model.Character
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import com.dmspallas.plumassignment.getValueForTest
import com.dmspallas.plumassignment.presentation.character.HireCharacterViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HireCharacterViewModelShould : BaseUnitTest() {
    private val repository: CharacterRepository = mock()
    private val characters = mock<List<Character>>()
    private val expected = Result.success(characters)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getCharactersFromRepository() {
        runTest {
            val viewModel = mockSuccessfulCase()
            viewModel.characters.getValueForTest()
            verify(repository, times(1)).getCharacters()
        }
    }

    @Test
    fun emitCharactersFromRepository() {
        runTest {
            val viewModel = mockSuccessfulCase()
            assertEquals(expected, viewModel.characters.getValueForTest())
        }
    }

    @Test
    fun emitErrorWhenReceiveError() {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(
                flow {
                    emit(Result.failure<List<Character>>(exception))
                }
            )
        }
        val viewModel = HireCharacterViewModel(repository)
        assertEquals(exception, viewModel.characters.getValueForTest()!!.exceptionOrNull())
    }


    private fun mockSuccessfulCase(): HireCharacterViewModel {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return HireCharacterViewModel(repository)
    }
}