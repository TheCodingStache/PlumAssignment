package com.dmspallas.plumassignment.presentation

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
import kotlinx.coroutines.flow.collect
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
    val heroes = repository.characters

    var hireState: MutableLiveData<Boolean> = MutableLiveData()
    var fireState: MutableLiveData<Boolean> = MutableLiveData()
    var buttonState: MutableLiveData<ButtonState> = MutableLiveData()

    @Bindable
    val saveButtonText = ObservableInt()

    @Bindable
    val deleteButtonText = ObservableInt()

    @Bindable
    val textViewName = MutableLiveData<String>()

    @Bindable
    val textViewDescription = MutableLiveData<String>()

    @Bindable
    val hireButtonVisibility = MutableLiveData<Int>()

    @Bindable
    val fireButtonVisibility = MutableLiveData<Int>()

    init {
        textViewName.value = heroName.toString()
        textViewDescription.value = heroDescription.toString()
        saveButtonText.set(R.string.save)
        deleteButtonText.set(R.string.delete)
        hireButtonVisibility.value = 0
        fireButtonVisibility.value = 0
    }

    fun saveOrCheck() {
        insert(CharacterEntity(0, heroName!!, heroDescription!!, heroImage!!))
        fireButtonVisibility.value = 0
        hireButtonVisibility.value = 8

    }

    fun deleteButton() {
        delete(heroName!!)
        hireButtonVisibility.value = 0
        fireButtonVisibility.value = 8

    }


    private fun saveState() {
        viewModelScope.launch {
            Dispatchers.IO
            impl.saveState(
                ButtonState(
                    hire = hireState.value!!, fire = fireState.value!!
                )
            )
        }
    }

    fun retrieveState() = viewModelScope.launch(Dispatchers.IO) {
        impl.getState().collect() { state ->
            buttonState.postValue(state)
        }
    }

    private fun insert(characterEntity: CharacterEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(characterEntity)
    }

    private fun delete(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(name)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}