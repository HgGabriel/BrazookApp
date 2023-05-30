package br.com.example.brazookatelas.ui.components.sections.series

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.components.items.SeriesItemGrid

@Composable
fun SeriesGrid(
    title: String,
    series: List<Series>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
            )


            LazyVerticalGrid(
                columns = GridCells.Fixed(2), Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)

            ) {

                items(series) { s ->
                    SeriesItemGrid(serie = s)
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesSectionPreview() {
    SeriesGrid(title = "Todos os Filmes", series = sampleSeries)
}