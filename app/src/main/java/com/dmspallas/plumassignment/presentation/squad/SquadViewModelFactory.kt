package com.dmspallas.plumassignment.presentation.squad

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import javax.inject.Inject

class SquadViewModelFactory @Inject constructor(
    owner: SavedStateRegistryOwner,
    private val repository: CharacterRepository,
    defaultArgs: Bundle? = null,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SquadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SquadViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}