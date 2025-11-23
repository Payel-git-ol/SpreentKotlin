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
    private val DEFAULT_COUNTRY_KEY = stringPreferencesKey("default_country")

    suspend fun saveDefaults(context: Context, city: String, country: String) {
        context.dataStore.edit { prefs ->
            prefs[DEFAULT_CITY_KEY] = city
            prefs[DEFAULT_COUNTRY_KEY] = country
        }
    }

    fun getDefaults(context: Context): Flow<Pair<String, String>> =
        context.dataStore.data.map { prefs ->
            val city = prefs[DEFAULT_CITY_KEY] ?: "Минск"
            val country = prefs[DEFAULT_COUNTRY_KEY] ?: "BY"
            city to country
        }
}
