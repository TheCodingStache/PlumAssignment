package com.dmspallas.plumassignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import javax.inject.Inject

class CharacterViewModelFactory @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterViewModel(repository) as T
    }
}