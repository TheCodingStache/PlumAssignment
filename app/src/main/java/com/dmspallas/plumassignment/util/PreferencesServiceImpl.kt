package com.dmspallas.plumassignment.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


const val name = "button_state"

class PreferencesServiceImpl(private val context: Context) : PreferencesService {


    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name
        )
        private val hire = intPreferencesKey("hire")
    }

    override suspend fun saveState(state: ButtonState) {
        context.dataStore.edit { settings ->
            settings[hire] = state.hire
        }
    }

    override suspend fun getState() = context.dataStore.data.map { settings ->
        ButtonState(
            hire = (settings[hire]!!)
        )
    }
}