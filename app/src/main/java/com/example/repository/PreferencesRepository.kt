package com.example.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "uzenglish_prefs")

data class LocalAppPreferences(
    val darkThemeEnabled: Boolean = false,
    val slowTtsDefault: Boolean = false,
    val showUzbekTranslationsDefault: Boolean = true,
    val robotOffsetX: Float = 0f,
    val robotOffsetY: Float = 0f
)

class PreferencesRepository(private val context: Context) {

    private object Keys {
        val DARK_THEME = booleanPreferencesKey("dark_theme_enabled")
        val SLOW_TTS = booleanPreferencesKey("slow_tts_default")
        val SHOW_UZ_TRANSLATION = booleanPreferencesKey("show_uz_translation")
        val ROBOT_X = floatPreferencesKey("robot_offset_x")
        val ROBOT_Y = floatPreferencesKey("robot_offset_y")
    }

    val preferencesFlow: Flow<LocalAppPreferences> = context.applicationContext.dataStore.data.map { prefs ->
        LocalAppPreferences(
            darkThemeEnabled = prefs[Keys.DARK_THEME] ?: false,
            slowTtsDefault = prefs[Keys.SLOW_TTS] ?: false,
            showUzbekTranslationsDefault = prefs[Keys.SHOW_UZ_TRANSLATION] ?: true,
            robotOffsetX = prefs[Keys.ROBOT_X] ?: 0f,
            robotOffsetY = prefs[Keys.ROBOT_Y] ?: 0f
        )
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.applicationContext.dataStore.edit { it[Keys.DARK_THEME] = enabled }
    }

    suspend fun setSlowTtsDefault(enabled: Boolean) {
        context.applicationContext.dataStore.edit { it[Keys.SLOW_TTS] = enabled }
    }

    suspend fun setShowUzbekTranslations(enabled: Boolean) {
        context.applicationContext.dataStore.edit { it[Keys.SHOW_UZ_TRANSLATION] = enabled }
    }

    suspend fun saveRobotOffset(x: Float, y: Float) {
        context.applicationContext.dataStore.edit {
            it[Keys.ROBOT_X] = x
            it[Keys.ROBOT_Y] = y
        }
    }
}
