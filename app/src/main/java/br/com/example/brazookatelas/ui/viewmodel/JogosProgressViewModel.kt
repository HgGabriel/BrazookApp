package br.com.example.brazookatelas.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.brazookatelas.model.GamerStatus
import br.com.example.brazookatelas.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JogosProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserProgressRepository(application)

    fun gamerStatusFlow(jogoId: String): Flow<GamerStatus> =
        repository.gamerStatus(jogoId)

    fun setGamerStatus(jogoId: String, status: GamerStatus) {
        viewModelScope.launch {
            repository.setGamerStatus(jogoId, status)
        }
    }
}
