package com.boxellogica.melodii

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "melodiiSettings")

object MelodiiPreferences {

    private val SWAP_SCREENS = booleanPreferencesKey("swapScreens")
    private val MUSIC_FOLDER_URI = stringPreferencesKey("musicFolderUri")

    fun getSwapScreens(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[SWAP_SCREENS] ?: false
        }
    }

    suspend fun setSwapScreens(context: Context, value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SWAP_SCREENS] = value
        }
    }

    fun getMusicFolder(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[MUSIC_FOLDER_URI]
        }
    }

    suspend fun setMusicFolder(context: Context, uri: String) {
        context.dataStore.edit { prefs ->
            prefs[MUSIC_FOLDER_URI] = uri
        }
    }
}