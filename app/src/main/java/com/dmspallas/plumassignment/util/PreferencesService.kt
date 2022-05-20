package com.dmspallas.plumassignment.util

import kotlinx.coroutines.flow.Flow

interface PreferencesService {

    suspend fun saveState(state: ButtonState)

    suspend fun getState() : Flow<ButtonState>
}