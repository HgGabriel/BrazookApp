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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.sampledata.sampleJogos
import br.com.example.brazookatelas.ui.components.items.JogoItemPager
import br.com.example.brazookatelas.ui.components.items.RatingBar
import br.com.example.brazookatelas.ui.components.items.notaJogos
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@Composable
fun JogosRowRecom(
    title: String,
    jogos: List<Jogos>,
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

        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(jogos) { j ->
                JogoItemPager(jogos = j)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JogosRowTrendPager(
    title: String,
    jogos: List<Jogos>,
    modifier: Modifier = Modifier,
) {

    Column() {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = { }) {
                Text(text = "Ver todos")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        val pagerStateJogos = rememberPagerState()
        HorizontalPager(
            pageCount = 5,
            state = pagerStateJogos,
            contentPadding = PaddingValues(horizontal = 65.dp),
            modifier = Modifier.height(385.dp)

        ) { page ->

            Box(
                Modifier
                    .wrapContentSize()
                    .graphicsLayer {
                        val pageOffsetJogos =
                            pagerStateJogos.calculateCurrentOffsetForPageJogos(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffsetJogos.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffsetJogos.coerceIn(0f, 1f)
                        )

                    }) {
                val images = jogos.map { it.capa }
                val nomes = jogos.map { it.nome }
                val ano = jogos.map { it.ano }
                val genero = jogos.map { it.genero }
                val plataformas = jogos.map { it.plataformas }


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.size(
                            width = 240.dp, height = 360.dp
                        )
                    ) {
                        Box {
                            AsyncImage(
                                model = images[page],
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.fobia),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .blur(
                                        radiusX = 25.dp,
                                        radiusY = 25.dp,
                                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(16.dp))
                                    ),
                                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                    setToScale(
                                        0.4f,
                                        0.4f,
                                        0.4f,
                                        1f
                                    )
                                }),
                            )
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
                                AsyncImage(
                                    model = images[page],
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp),
                                    placeholder = painterResource(id = R.drawable.fobia),

                                    )
                            }

                            Text(
                                text = "${nomes[page]} \n (${ano[page]})",
                                color = Color.White,
                                style = MaterialTheme.typography.h4,
                                maxLines = 3,
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(150.dp)
                                    .padding(bottom = 4.dp)
                            )


                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxSize()
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {


                                    RatingBar(rating = notaJogos)
                                }

                                Text(
                                    text = genero[page],
                                    color = MaterialTheme.colors.onBackground,
                                    style = MaterialTheme.typography.subtitle1,
                                    maxLines = 2,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text(
                                    text = plataformas[page],
                                    color = MaterialTheme.colors.onBackground,
                                    style = MaterialTheme.typography.subtitle1,
                                    maxLines = 2,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                )
                            }

                        }
                    }
                }
            }
        }
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
                    if (pagerStateJogos.currentPage == it) Color.DarkGray else Color.LightGray
                Box(modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(12.dp)
                    .background(color = color)
                    .clickable {
                        scope.launch {
                            pagerStateJogos.animateScrollToPage(it)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPageJogos(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JogosRowTrendPagerPreview() {
    JogosRowTrendPager(title = "Em alta", sampleJogos)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JogosRowTrendPreview() {
    JogosRowRecom(title = "Em alta", jogos = sampleJogos)
}