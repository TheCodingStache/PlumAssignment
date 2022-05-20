package com.dmspallas.plumassignment.presentation.squad

import android.view.View
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.dmspallas.plumassignment.util.ButtonState
import com.dmspallas.plumassignment.R
import com.dmspallas.plumassignment.data.remote.db.CharacterEntity
import com.dmspallas.plumassignment.data.remote.db.CharacterRepository
import com.dmspallas.plumassignment.util.PreferencesServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquadViewModel @Inject constructor(
    private val repository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
    private val impl: PreferencesServiceImpl
) : ViewModel(),
    Observable {
    private val heroName = savedStateHandle.get<String>("name")
    private val heroDescription = savedStateHandle.get<String>("description")
    private val heroImage = savedStateHandle.get<String>("image")
    val heroes: LiveData<List<CharacterEntity>> = repository.characters.asLiveData()

    @Bindable
    val saveButtonText = ObservableInt()

    @Bindable
    val hireButtonVisibility = MutableLiveData<Int>()

    @Bindable
    val textViewName = MutableLiveData<String>()

    @Bindable
    val textViewDescription = MutableLiveData<String>()


    init {
        textViewName.value = heroName.toString()
        textViewDescription.value = heroDescription.toString()
        saveButtonText.set(R.string.save)
    }

    fun saveButton() {
        insert(CharacterEntity(0, heroName!!, heroDescription!!, heroImage!!))
    }

    private fun insert(characterEntity: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(characterEntity)
    }

    fun existsByName(name: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val returnValue = repository.check(name)
            result.postValue(returnValue)
        }
        return result
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}