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
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme
import br.com.example.brazookatelas.sampledata.sampleSectionsFilmes
import br.com.example.brazookatelas.ui.components.sections.filmes.FilmesColumnRes

class Filmes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FilmesScreen()
                }
            }
        }
    }
}


@Composable
fun FilmesScreen() {
    Column{
            FilmesColumnRes(sections = sampleSectionsFilmes)
        }
    }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesTelaPreview() {
    FilmesScreen()
}