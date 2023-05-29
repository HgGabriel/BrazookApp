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
import br.com.example.brazookatelas.sampledata.sampleLivros
import br.com.example.brazookatelas.sampledata.sampleSectionsLivros
import br.com.example.brazookatelas.ui.components.sections.livros.LivrosColumnRes
import br.com.example.brazookatelas.ui.components.sections.livros.LivrosList
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class LivrosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Box(modifier = Modifier.background(brush = gradient)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        LivrosList(title = "Todos os Livros",livros = sampleLivros.sortedBy {livros ->
                            livros.nome
                        })
                    }
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