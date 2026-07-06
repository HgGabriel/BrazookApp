package br.com.example.brazookatelas.repository

import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.sampledata.*

class MediaRepository {

    fun getMovies(): List<MediaItem> = sampleFilmes.sortedBy { it.title }
    
    fun getSeries(): List<MediaItem> = sampleSeries.sortedBy { it.title }
    
    fun getBooks(): List<MediaItem> = sampleLivros.sortedBy { it.title }
    
    fun getGames(): List<MediaItem> = sampleJogos.sortedBy { it.title }

    fun searchMedia(query: String, type: String): List<MediaItem> {
        val source = when (type.lowercase()) {
            "filme" -> getMovies()
            "serie" -> getSeries()
            "livro" -> getBooks()
            "jogo" -> getGames()
            else -> emptyList()
        }
        if (query.isBlank()) return source
        return source.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }
}
