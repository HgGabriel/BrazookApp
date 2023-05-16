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
import br.com.example.brazookatelas.sampledata.sampleSectionsSeries
import br.com.example.brazookatelas.ui.components.sections.filmes.SeriesColumnRes
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class Series : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrazookaTelasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SeriesScreen()
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