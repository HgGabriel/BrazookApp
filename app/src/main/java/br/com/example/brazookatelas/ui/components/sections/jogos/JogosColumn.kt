package br.com.example.brazookatelas.ui.components.sections.jogos

import JogosRowRecom
import JogosRowTrendPager
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
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.sampledata.sampleJogos
import br.com.example.brazookatelas.ui.components.CategoriesJogos
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.JogoItemList
import br.com.example.brazookatelas.ui.screens.FilmesActivity
import br.com.example.brazookatelas.ui.screens.JogosActivity

@Composable
fun JogosColumnRes(
    sections: Map<String, List<Jogos>>,
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
        val searchedGame = remember(text) {
            if (text.isNotBlank()) {
                sampleJogos.filter { jogo ->
                    jogo.nome.contains(
                        text,
                        ignoreCase = true
                    ) ||
                            jogo.sinopse.contains(
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
                        JogosTelaColumn()
                        Divider(thickness = 4.dp)
                        Text(
                            text = "Outros",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }

                }

                item {

                }

                val outrosLista = sampleJogos.sortedBy{ jogos ->
                    jogos.nome
                }.take(5)
                items(outrosLista){j ->
                    JogoItemList(
                        jogos = j
                    )
                }

                item{
                    TextButton(onClick = {
                        fContext.startActivity(Intent(fContext, JogosActivity::class.java))
                    }) {
                        Text(text = "Ver mais", fontSize = 20.sp)
                    }
                }

            } else {
                items(searchedGame) { j ->
                    JogoItemList(
                        jogos = j,
                    )
                }

            }
        }
    }

}

@Composable
fun JogosList(title: String = "",
    jogos: List<Jogos>,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
        )

        LazyColumn(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(jogos) { j ->
                JogoItemList(jogos = j)
            }
        }
    }
}

@Composable
fun JogosTelaColumn() {
    Column {
        JogosRowTrendPager(title = "Em alta", jogos = sampleJogos.sortedByDescending { jogos->
            jogos.ano
        }.take(5))
        CategoriesJogos()
        JogosRowRecom(title = "Recomendações", jogos = sampleJogos.sortedBy { jogos ->
            jogos.ano
        }.take(9))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun JogosTelaColumnPreview() {
    JogosTelaColumn()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun JogosListPreview() {
    JogosList(jogos = sampleJogos)
}