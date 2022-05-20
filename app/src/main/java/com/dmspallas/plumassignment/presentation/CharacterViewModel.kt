package com.dmspallas.plumassignment.presentation

import androidx.databinding.Observable
import androidx.lifecycle.*
import com.dmspallas.plumassignment.data.remote.db.CharacterDao
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity
import com.dmspallas.plumassignment.domain.model.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    val characters = liveData {
        loader.postValue(true)
        emitSource(
            repository.getCharacters()
                .onEach {
                    loader.postValue(false)
                }
                .asLiveData()
        )
    }
}