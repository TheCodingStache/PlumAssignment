package com.dmspallas.plumassignment.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import javax.inject.Inject

class HireCharacterViewModelFactory @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HireCharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HireCharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}