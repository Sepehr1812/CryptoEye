package com.nebka.cryptoeye.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * A DataStore class to keep last update time of tags.
 */
class TimeDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val LAST_UPDATE_TIME = longPreferencesKey("lastUpdateTime")
    }

    val getLastUpdateTime = dataStore.data.map { preferences -> preferences[LAST_UPDATE_TIME] ?: 0 }

    suspend fun saveLastUpdateTime(time: Long) {
        dataStore.edit { preferences -> preferences[LAST_UPDATE_TIME] = time }
    }
}