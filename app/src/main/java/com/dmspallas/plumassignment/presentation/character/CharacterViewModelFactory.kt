package com.dmspallas.plumassignment.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import com.dmspallas.plumassignment.presentation.squad.FireSquadViewModel
import javax.inject.Inject

class CharacterViewModelFactory @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}