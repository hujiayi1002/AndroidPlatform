package com.ocse.baseandroid.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DataStoreUtils {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataStoreUtils")
        private val dataStore: DataStore<Preferences> = ObtainApplication.app!!.dataStore

        fun getString(key: String): String {
            var returnValue = ""
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.data.map {
                    returnValue = it[stringPreferencesKey(key)] ?: ""
                }
            }
            return returnValue
        }

        fun setString(key: String, value: String) {
            CoroutineScope(Dispatchers.IO).launch {
                dataStore.edit {
                    it[stringPreferencesKey(key)] = value
                }
            }

        }
    }
}