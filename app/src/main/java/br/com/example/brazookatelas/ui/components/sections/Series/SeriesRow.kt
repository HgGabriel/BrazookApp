package br.com.example.brazookatelas.ui.components.sections.row

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.components.items.SerieItemPager
import br.com.example.brazookatelas.ui.components.items.SerieItemRow
import br.com.example.brazookatelas.ui.screens.LivrosActivity
import br.com.example.brazookatelas.ui.screens.SeriesActivity
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@Composable
fun SeriesRowRecom(
    title: String,
    series: List<Series>,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Column(modifier) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = {fContext.startActivity(Intent(fContext, SeriesActivity::class.java)) }) {
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
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(series) { s ->
                SerieItemRow(serie = s)
            }
        }
    }
}

@Composable
fun SerieRowTrend(
    title: String,
    series: List<Series>,
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
                fContext.startActivity(Intent(fContext, SeriesActivity::class.java))
            }) {
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
            items(series) { s ->
                SerieItemPager(serie = s)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SerieRowTrendPager(
    title: String,
    series: List<Series>,
    modifier: Modifier = Modifier,

    ) {
    val fContext = LocalContext.current
    Column{

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = {
                fContext.startActivity(Intent(fContext, SeriesActivity::class.java))
            }) {
                Text(text = "Ver todos")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val images = series.map { it.poster }
        val nomes = series.map { it.nome }
        val pagerStateSeries = rememberPagerState()

        HorizontalPager(
            pageCount = 5,
            state = pagerStateSeries,
            contentPadding = PaddingValues(horizontal = 65.dp),
            modifier = Modifier
                .height(385.dp)

        ) { page ->
            Box(
                Modifier
                    .wrapContentSize()
                    .graphicsLayer {
                        val pageOffset =
                            pagerStateSeries.calculateCurrentOffsetForPageSeries(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
            ) {
                Surface(
                    modifier = Modifier
                        .size(width = 240.dp, height = 360.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier.size(width = 240.dp, height = 330.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround

                    ) {

                        AsyncImage(
                            model = images[page],
                            contentDescription = "Imagens dos filmes",
                            placeholder = painterResource(id = R.drawable.mundoluna),
                            modifier = Modifier.width(240.dp),
                            contentScale = ContentScale.FillBounds
                        )

                        Text(
                            text = nomes[page],
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                        )
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(5) { it ->
                val scope = rememberCoroutineScope()
                val color =
                    if (pagerStateSeries.currentPage == it) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .size(12.dp)
                        .background(color = color)
                        .clickable {
                            scope.launch{
                                pagerStateSeries.animateScrollToPage(it)
                            }
                        }
                ) {
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPageSeries(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SerieRowTrendPagerPreview() {
    SerieRowTrendPager(title = "Em Alta", series = sampleSeries)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesRowRecomPreview() {
    SeriesRowRecom(title = "Recomendações", series = sampleSeries)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SeriesRowTrendPrev() {
    SerieRowTrend(title = "Em alta", series = sampleSeries)
}
