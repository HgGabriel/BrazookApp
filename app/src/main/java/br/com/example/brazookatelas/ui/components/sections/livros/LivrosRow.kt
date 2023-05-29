package br.com.example.brazookatelas.ui.components.sections.livros

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.Livros
import br.com.example.brazookatelas.sampledata.sampleLivros
import br.com.example.brazookatelas.ui.components.items.LivroItemPager
import br.com.example.brazookatelas.ui.components.items.LivroItemRow
import br.com.example.brazookatelas.ui.screens.JogosActivity
import br.com.example.brazookatelas.ui.screens.LivrosActivity

@Composable
fun LivrosRowRecom(
    title: String,
    livros: List<Livros>,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Column(modifier = Modifier.padding(bottom = 16.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                fContext.startActivity(Intent(fContext, LivrosActivity::class.java))
            }) {
                Text(text = "Ver todos")
            }
        }
        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(livros) { l ->
                LivroItemRow(livros = l)
            }
        }
    }
}

@Composable
fun LivrosRowTrend(
    title: String,
    livros: List<Livros>,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Column(modifier) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                fContext.startActivity(Intent(fContext, LivrosActivity::class.java))
            }) {
                Text(text = "Ver todos")
            }
        }

        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(livros) { l ->
                LivroItemPager(livros = l)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LivrosRowTrendPreview() {
    LivrosRowTrend(title = "Em alta", livros = sampleLivros.shuffled())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LivrosRowRecomPreview() {
    LivrosRowRecom(title = "Recomendações", livros = sampleLivros)
}
