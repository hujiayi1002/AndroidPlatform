package com.ocse.baseandroid.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreUtils() {
    companion object{
        private val dataStore: DataStore<Preferences> = ObtainApplication.getApp().createDataStore(
            name = "DataStoreUtils"
        )

         fun getString(key: String): Flow<String> {
            return dataStore.data.map {
                it[preferencesKey<String>(key)] ?: ""
            }
        }

        suspend fun setString(key: String, value: String) {
            dataStore.edit {
                it[preferencesKey<String>(key)] = value
            }
        }
    }

}