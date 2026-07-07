package br.com.example.brazookatelas.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.example.brazookatelas.model.GamerStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userProgressDataStore by preferencesDataStore(name = "user_progress")

/**
 * Persiste o progresso de "episódios vistos" por série e de "páginas lidas" por livro,
 * sobrevivendo à navegação de volta e ao reinício do processo. Chaveado por item
 * (`"watched_episodes_$serieId"` / `"reading_progress_$livroId"`) para não colidir a
 * numeração local entre itens diferentes.
 */
class UserProgressRepository(private val context: Context) {

    private fun watchedKey(serieId: String) = stringSetPreferencesKey("watched_episodes_$serieId")

    fun watchedEpisodes(serieId: String): Flow<Set<Int>> =
        context.userProgressDataStore.data.map { prefs ->
            prefs[watchedKey(serieId)]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }

    suspend fun toggleEpisode(serieId: String, episodeIndex: Int) {
        context.userProgressDataStore.edit { prefs ->
            val key = watchedKey(serieId)
            val current = prefs[key]?.toMutableSet() ?: mutableSetOf()
            val episodeKey = episodeIndex.toString()
            if (!current.remove(episodeKey)) {
                current.add(episodeKey)
            }
            prefs[key] = current
        }
    }

    private fun readingProgressKey(livroId: String) = intPreferencesKey("reading_progress_$livroId")

    fun readingProgress(livroId: String): Flow<Int> =
        context.userProgressDataStore.data.map { prefs -> prefs[readingProgressKey(livroId)] ?: 0 }

    suspend fun setReadingProgress(livroId: String, pagesRead: Int) {
        context.userProgressDataStore.edit { prefs ->
            prefs[readingProgressKey(livroId)] = pagesRead
        }
    }

    private fun gamerStatusKey(jogoId: String) = stringPreferencesKey("gamer_status_$jogoId")

    fun gamerStatus(jogoId: String): Flow<GamerStatus> =
        context.userProgressDataStore.data.map { prefs ->
            prefs[gamerStatusKey(jogoId)]
                ?.let { stored -> runCatching { GamerStatus.valueOf(stored) }.getOrNull() }
                ?: GamerStatus.QUERO_JOGAR
        }

    suspend fun setGamerStatus(jogoId: String, status: GamerStatus) {
        context.userProgressDataStore.edit { prefs ->
            prefs[gamerStatusKey(jogoId)] = status.name
        }
    }
}
