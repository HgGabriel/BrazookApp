package br.com.example.brazookatelas.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.example.brazookatelas.sampledata.sampleSectionsLivros
import br.com.example.brazookatelas.ui.components.sections.livros.LivrosColumnRes
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class Livros : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LivrosScreen()
                }
            }
        }
    }
}


@Composable
fun LivrosScreen() {
    Column {
        LivrosColumnRes(sections = sampleSectionsLivros)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LivrosScreenPreview() {
    LivrosScreen()
}