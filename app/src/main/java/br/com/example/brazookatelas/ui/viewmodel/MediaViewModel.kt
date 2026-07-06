package br.com.example.brazookatelas.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.repository.MediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaViewModel(private val repository: MediaRepository = MediaRepository()) : ViewModel() {

    // Estados para cada tipo de aba
    private val _searchQueries = MutableStateFlow(mapOf("filme" to "", "serie" to "", "livro" to "", "jogo" to ""))
    
    private val _filmesUiState = MutableStateFlow<List<MediaItem>>(repository.getMovies())
    val filmesUiState: StateFlow<List<MediaItem>> = _filmesUiState.asStateFlow()

    private val _seriesUiState = MutableStateFlow<List<MediaItem>>(repository.getSeries())
    val seriesUiState: StateFlow<List<MediaItem>> = _seriesUiState.asStateFlow()

    private val _livrosUiState = MutableStateFlow<List<MediaItem>>(repository.getBooks())
    val livrosUiState: StateFlow<List<MediaItem>> = _livrosUiState.asStateFlow()

    private val _jogosUiState = MutableStateFlow<List<MediaItem>>(repository.getGames())
    val jogosUiState: StateFlow<List<MediaItem>> = _jogosUiState.asStateFlow()

    fun getSearchQuery(type: String): String = _searchQueries.value[type] ?: ""

    fun onSearchQueryChanged(type: String, query: String) {
        val updatedQueries = _searchQueries.value.toMutableMap()
        updatedQueries[type] = query
        _searchQueries.value = updatedQueries

        val filteredList = repository.searchMedia(query, type)
        when (type.lowercase()) {
            "filme" -> _filmesUiState.value = filteredList
            "serie" -> _seriesUiState.value = filteredList
            "livro" -> _livrosUiState.value = filteredList
            "jogo" -> _jogosUiState.value = filteredList
        }
    }
}
