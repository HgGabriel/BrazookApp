package br.com.example.brazookatelas.ui.components.sections.grid

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
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.ui.components.items.FilmeItemGrid


@Composable
fun FilmesGrid(
    title: String,
    filmes: List<Filmes>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(2), Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)

        ) {

            items(filmes) { f ->
                FilmeItemGrid(filme = f)
            }


        }
    }
}

//@Composable
//fun FilmesGridMore(
//    title: String,
//    filmes: List<Filmes>,
//    modifier: Modifier = Modifier,
//) {
//    Column(modifier) {
//
//        Text(
//            text = title,
//            style = MaterialTheme.typography.h6,
//            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
//        )
//
//
//        Flow(
//            columns = GridCells.Fixed(2), Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(16.dp)
//
//        ) {
//
//            items(filmes) { f ->
//                FilmeItemMore(filme = f)
//            }
//
//
//        }
//    }
//}
//
//@Preview
//@Composable
//fun FilmeGridMorePreview() {
//    FilmesGridMore(title = "Outros", filmes = sampleFilmes)
//}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmesSectionPreview() {
    FilmesGrid(
        title = "Todos os Filmes", filmes = sampleFilmes, modifier = Modifier
    )

}