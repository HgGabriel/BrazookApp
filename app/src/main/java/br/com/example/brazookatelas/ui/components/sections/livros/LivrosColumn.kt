package br.com.example.brazookatelas.ui.components.sections.livros

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
import br.com.example.brazookatelas.model.Livros
import br.com.example.brazookatelas.sampledata.sampleLivros
import br.com.example.brazookatelas.sampledata.sampleSectionsLivros
import br.com.example.brazookatelas.ui.components.CategoriesLivros
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.LivroItemList
import br.com.example.brazookatelas.ui.screens.LivrosActivity

@Composable
fun LivrosColumnRes(
    sections: Map<String, List<Livros>>,
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
        val searchedBook = remember(text) {
            if (text.isNotBlank()) {
                sampleLivros.filter { livro ->
                    livro.nome.contains(
                        text,
                        ignoreCase = true
                    ) ||
                            livro.sinopse.contains(
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
                        LivrosTelaColumn()
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

                val outrosLista = sampleLivros.sortedBy{livros ->
                    livros.nome
                }.take(5)
                items(outrosLista){l4 ->
                    LivroItemList(
                        livros = l4
                    )
                }

                item{
                    TextButton(onClick = {
                        fContext.startActivity(Intent(fContext, LivrosActivity::class.java))
                    }) {
                        Text(text = "Ver mais", fontSize = 20.sp)
                    }
                }

            } else {
                items(searchedBook) { l2 ->
                    LivroItemList(
                        livros = l2,
                    )
                }

            }
        }
    }

}

@Composable
fun LivrosList(title:String = "",
    livros: List<Livros>,
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
            items(livros) { l ->
                LivroItemList(livros = l)
            }
        }
    }
}


@Composable
fun LivrosTelaColumn() {
    Column {
        LivrosRowTrend(title = "Em alta", livros = sampleLivros.sortedByDescending { livros ->
            livros.ano
        }.take(5))
        CategoriesLivros()
        LivrosRowRecom(title = "Recomendações", livros = sampleLivros.take(6).shuffled())
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LivrosColumnResPreview() {
    LivrosColumnRes(sections = sampleSectionsLivros)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LivrosTelaColumnPreview() {
    LivrosTelaColumn()
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LivrosListPreview() {
    LivrosList(livros = sampleLivros.shuffled())
}