package br.com.example.brazookatelas.ui.screens.Details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.model.Livros
import br.com.example.brazookatelas.ui.components.sections.jogos.DetailScreenJogos
import br.com.example.brazookatelas.ui.components.sections.livros.DetailScreenLivros
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class DetailsLivrosActivity : ComponentActivity() {

    private val livros: Livros by lazy {
        intent?.getSerializableExtra(LIVROS_ID) as Livros
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                DetailScreenLivros(livros = livros)
            }
        }
    }
    companion object {
        private const val LIVROS_ID = "livros_id"
        fun newIntent(context: Context, livros: Livros) =
            Intent(context, DetailsLivrosActivity::class.java).apply {
                putExtra(LIVROS_ID, livros)
            }
    }
}


