package br.com.example.brazookatelas.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.brazookatelas.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SeriesProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserProgressRepository(application)

    fun watchedEpisodesFlow(serieId: String): Flow<Set<Int>> =
        repository.watchedEpisodes(serieId)

    fun toggleEpisode(serieId: String, episodeIndex: Int) {
        viewModelScope.launch {
            repository.toggleEpisode(serieId, episodeIndex)
        }
    }
}
