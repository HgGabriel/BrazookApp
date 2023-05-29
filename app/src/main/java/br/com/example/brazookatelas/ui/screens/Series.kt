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
import br.com.example.brazookatelas.sampledata.sampleSectionsSeries
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.components.sections.Series.SeriesGrid
import br.com.example.brazookatelas.ui.components.sections.filmes.SeriesColumnRes
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class SeriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Box(modifier = Modifier.background(brush = gradient)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        SeriesGrid(title = "Todas as séries", series = sampleSeries.sortedBy {series ->
                            series.nome
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun SeriesScreen() {
    Column {
        SeriesColumnRes(sections = sampleSectionsSeries)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesScreenPreview() {
    SeriesScreen()
}