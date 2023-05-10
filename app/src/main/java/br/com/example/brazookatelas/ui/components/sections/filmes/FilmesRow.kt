package br.com.example.brazookatelas.ui.components.sections.row

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.ui.components.items.FilmeItemPager
import br.com.example.brazookatelas.ui.components.items.FilmeItemRow


@Composable
fun FilmesRowRecom(
    title: String,
    filmes: List<Filmes>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = { }) {
                Text(text = "Ver tudo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(filmes) { f ->
                FilmeItemRow(filme = f)
            }
        }
    }
}

@Composable
fun FilmeRowTrend(
    title: String,
    filmes: List<Filmes>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = { }) {
                Text(text = "Ver todos")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(filmes) { f2 ->
                FilmeItemPager(filme = f2)
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesRowRecomPreview() {
    FilmesRowRecom(title = "Recomendações", filmes = sampleFilmes)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesRowTrendPrev() {
    FilmeRowTrend(title = "Em alta", filmes = sampleFilmes)
}
