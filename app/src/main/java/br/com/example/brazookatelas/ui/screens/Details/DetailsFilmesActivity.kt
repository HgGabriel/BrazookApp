package br.com.example.brazookatelas.ui.screens.Details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.ui.components.sections.filmes.DetailScreenFilmes
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class DetailsFilmesActivity : ComponentActivity() {

    private val filme: Filmes by lazy {
        intent?.getSerializableExtra(FILMES_ID) as Filmes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                DetailScreenFilmes(filmes = filme)
            }
        }
    }
    companion object {
        private const val FILMES_ID = "filmes_id"
        fun newIntent(context: Context, filmes: Filmes) =
            Intent(context, DetailsFilmesActivity::class.java).apply {
                putExtra(FILMES_ID, filmes)
            }
    }
}


