package br.com.example.brazookatelas.ui.components.sections.filmes

import android.content.Intent
import androidx.compose.foundation.clickable
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
import androidx.navigation.compose.rememberNavController
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.route.AppRouteName
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.sampledata.sampleSectionsFilmes
import br.com.example.brazookatelas.ui.components.CategoriesFilmes
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.FilmeCardItem
import br.com.example.brazookatelas.ui.components.items.FilmeItemGrid
import br.com.example.brazookatelas.ui.components.sections.row.FilmeRowTrendPager
import br.com.example.brazookatelas.ui.components.sections.row.FilmesRowRecom
import br.com.example.brazookatelas.ui.screens.FilmesActivity


val outrosFilmes = sampleFilmes.sortedBy { filme ->
    filme.filme
}.take(9)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilmesColumnRes(
    searchText: String = "",
    onNavigateToDetails: (Filmes) -> Unit = {},
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
                sampleFilmes.filter { filme ->
                    filme.filme.contains(
                        text,
                        ignoreCase = true
                    ) ||
                            filme.sinopse.contains(
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
                        FilmesTelaColumn()
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
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(10) {
                            FilmeItemGrid(filme = sampleFilmes[it],
                            )
                        }
                    }
                }

                item {
                    TextButton(onClick = {
                        fContext.startActivity(Intent(fContext, FilmesActivity::class.java))
                    }) {
                        Text(text = "Ver mais", fontSize = 20.sp)
                    }
                }

            } else {
                items(searchedMovie) { f ->
                    FilmeCardItem(
                        filme = f,
                        Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onNavigateToDetails(f)
                            },
                    )
                }

            }
        }
    }

}

@Composable
fun FilmesTelaColumn() {
    val navControler = rememberNavController()
    Column {
        FilmeRowTrendPager(title = "Em alta", filmes = sampleFilmes.sortedByDescending { filmes ->
            filmes.ano
        }.take(5),  onNavigateToDetails = { filmes ->
            navControler.navigate("${AppRouteName.DetailFilmes}/${filmes.id}")
        })
        Spacer(Modifier.height(16.dp))
        CategoriesFilmes()
        Spacer(Modifier.height(16.dp))
        FilmesRowRecom(title = "Recomendações", filmes = sampleFilmes.sortedByDescending { filmes ->
            filmes.nota
        }, onNavigateToDetails = { filmes ->
            navControler.navigate("${AppRouteName.DetailFilmes}/${filmes.id}")
        })
        Spacer(Modifier.height(16.dp))
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun FilmesTelaColumnPreview() {
//    FilmesTelaColumn(navControler = )
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesColumnResPreview() {
    FilmesColumnRes()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesColumnResPreviewWithText() {
    FilmesColumnRes()
}
