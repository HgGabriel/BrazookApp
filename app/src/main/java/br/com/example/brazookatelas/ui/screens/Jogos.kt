package br.com.example.brazookatelas.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.example.brazookatelas.gradient
import br.com.example.brazookatelas.sampledata.sampleJogos
import br.com.example.brazookatelas.sampledata.sampleSectionsJogos
import br.com.example.brazookatelas.ui.components.sections.jogos.JogosColumnRes
import br.com.example.brazookatelas.ui.components.sections.jogos.JogosList
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class JogosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Box(modifier = Modifier.background(brush = gradient)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        JogosList(title = "Todos os Jogos", jogos = sampleJogos.sortedBy {jogos ->
                            jogos.nome
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun JogosScreen() {
    Column {
        JogosColumnRes(sections = sampleSectionsJogos)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JogosScreenPreview() {
    JogosScreen()
}