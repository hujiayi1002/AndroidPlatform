package com.ocse.baseandroid.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class DataStoreUtils() {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataStoreUtils")
        private val dataStore: DataStore<Preferences> = ObtainApplication.getApp().dataStore

        fun getString(key: String): String {
            var returnValue = ""
            runBlocking {
                dataStore.data.first {
                    returnValue = it[stringPreferencesKey(key)] ?: returnValue
                    true
                }
            }
            return returnValue
        }

         fun setString(key: String, value: String) {
             runBlocking {  dataStore.edit {
                 it[stringPreferencesKey(key)] = value
             } }

        }
    }
}