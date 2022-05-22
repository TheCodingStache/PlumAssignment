package com.dmspallas.plumassignment.presentation.squad

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireSquadViewModel @Inject constructor(
    private val repository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    Observable {

    private val heroName = savedStateHandle.get<String>("name")
    private val heroDescription = savedStateHandle.get<String>("description")
    val heroes : LiveData<List<CharacterEntity>> = repository.characters.asLiveData()

    @Bindable
    val deleteButtonText = ObservableInt()

    @Bindable
    val textViewName = MutableLiveData<String>()

    @Bindable
    val textViewDescription = MutableLiveData<String>()


    init {
        textViewName.value = heroName.toString()
        textViewDescription.value = heroDescription.toString()
        deleteButtonText.set(R.string.delete)
    }

    fun deleteButton() {
        delete(heroName!!)
    }

    private fun delete(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(name)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}