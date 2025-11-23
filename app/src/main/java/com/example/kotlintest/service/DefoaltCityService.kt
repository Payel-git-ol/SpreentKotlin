package com.example.kotlintest.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object CityManager {
    private val DEFAULT_CITY_KEY = stringPreferencesKey("default_city")

    suspend fun saveDefaultCity(context: Context, city: String) {
        context.dataStore.edit { prefs ->
            prefs[DEFAULT_CITY_KEY] = city
        }
    }

    fun getDefaultCity(context: Context): Flow<String> =
        context.dataStore.data.map { prefs ->
            prefs[DEFAULT_CITY_KEY] ?: "Минск"
        }
}