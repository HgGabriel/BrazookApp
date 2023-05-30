package br.com.example.brazookatelas.ui.screens.Details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.ui.components.sections.jogos.DetailScreenJogos
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class DetailsJogosActivity : ComponentActivity() {

    private val jogos: Jogos by lazy {
        intent?.getSerializableExtra(JOGOS_ID) as Jogos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                DetailScreenJogos(jogos = jogos)
            }
        }
    }
    companion object {
        private const val JOGOS_ID = "jogos_id"
        fun newIntent(context: Context, jogos: Jogos) =
            Intent(context, DetailsJogosActivity::class.java).apply {
                putExtra(JOGOS_ID, jogos)
            }
    }
}


