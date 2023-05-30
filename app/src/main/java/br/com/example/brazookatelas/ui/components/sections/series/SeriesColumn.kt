package br.com.example.brazookatelas.ui.components.sections.filmes

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.sampledata.sampleSectionsSeries
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.SerieCardItem
import br.com.example.brazookatelas.ui.components.items.SeriesItemGrid
import br.com.example.brazookatelas.ui.components.sections.series.CategoriesSeries
import br.com.example.brazookatelas.ui.components.sections.row.SerieRowTrendPager
import br.com.example.brazookatelas.ui.components.sections.row.SeriesRowRecom
import br.com.example.brazookatelas.ui.screens.SeriesActivity

val outrasSeries = sampleSeries.sortedBy { serie ->
    serie.nome
}.take(9)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SeriesColumnRes(
    sections: Map<String, List<Series>>,
    searchText: String = "",
) {
    val fContext = LocalContext.current
    Column {
        var text by remember {
            mutableStateOf(searchText)
        }
        SearchTextField(
            searchText = text,
            onSearchChange = {
                text = it
            },
        )
        val searchedMovie = remember(text) {
            if (text.isNotBlank()) {
                sampleSeries.filter { serie ->
                    serie.nome.contains(
                        text,
                        ignoreCase = true
                    ) ||
                            serie.sinopse.contains(
                                text,
                                ignoreCase = true
                            )
                }
            } else emptyList()
        }
        LazyColumn(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (text.isBlank()) {
                item {
                    Column(horizontalAlignment = Alignment.Start) {
                        SeriesTelaColumn()
                        Divider(thickness = 4.dp)
                        Text(
                            text = "Outros",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }
                }

                item {
                    FlowRow(
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(10) {
                            SeriesItemGrid(serie = sampleSeries[it])
                        }
                    }
                }

                item {
                    TextButton(onClick = {
                        fContext.startActivity(Intent(fContext, SeriesActivity::class.java))
                    }) {
                        Text(text = "Ver mais", fontSize = 20.sp)
                    }
                }

            } else {
                items(searchedMovie) { s ->
                    SerieCardItem(
                        serie = s,
                        Modifier.padding(horizontal = 16.dp),
                    )
                }

            }
        }
    }

}

@Composable
fun SeriesTelaColumn() {
    Column {
        SerieRowTrendPager(title = "Em alta", series = sampleSeries.sortedByDescending { series ->
            series.ano
        }.take(5))
        Spacer(Modifier.height(16.dp))
        CategoriesSeries()
        Spacer(Modifier.height(16.dp))
        SeriesRowRecom(title = "Recomendações", series = sampleSeries.sortedByDescending { series ->
            series.nota
        })
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SeriesTelaColumnPreview() {
    SeriesTelaColumn()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesColumnResPreview() {
    SeriesColumnRes(sampleSectionsSeries)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesColumnResPreviewWithText() {
    SeriesColumnRes(sampleSectionsSeries, "aa")
}
