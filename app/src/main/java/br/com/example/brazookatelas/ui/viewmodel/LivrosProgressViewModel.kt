package br.com.example.brazookatelas.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.brazookatelas.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LivrosProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserProgressRepository(application)

    fun readingProgressFlow(livroId: String): Flow<Int> =
        repository.readingProgress(livroId)

    fun setReadingProgress(livroId: String, pagesRead: Int) {
        viewModelScope.launch {
            repository.setReadingProgress(livroId, pagesRead)
        }
    }
}
