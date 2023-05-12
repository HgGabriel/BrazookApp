package br.com.example.brazookatelas.ui.components.sections.row

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.ui.components.items.FilmeItemPager
import br.com.example.brazookatelas.ui.components.items.FilmeItemRow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.math.absoluteValue


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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmeRowTrendPager(
    title: String,
    filmes: List<Filmes>,
    modifier: Modifier = Modifier,

) {

    Column(modifier = Modifier.fillMaxSize()) {

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

        val images = filmes.map {it.poster}
        val pagerState = rememberPagerState()

        HorizontalPager(
             pageCount= 5,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 65.dp),
            modifier = Modifier
                .height(350.dp)
        ) { page ->
            Box(Modifier
                .graphicsLayer {
                    val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                    // translate the contents by the size of the page, to prevent the pages from sliding in from left or right and stays in the center
                    translationX = pageOffset * size.width
                    // apply an alpha to fade the current page in and the old page out
                    alpha = 1 - pageOffset.absoluteValue
                }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagens dos filmes",
                    placeholder = painterResource(id = R.drawable.mundoluna),
                    modifier = Modifier.width(240.dp)
                )

            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilmeRowTrendPagerPreview() {
    FilmeRowTrendPager(title = "Em Alta", filmes = sampleFilmes)
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
