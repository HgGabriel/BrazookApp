package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.screens.Details.DetailsFilmesActivity
import br.com.example.brazookatelas.ui.screens.Details.DetailsSeriesActivity
import coil.compose.AsyncImage


@Composable
fun SeriesItemGrid(
    serie: Series,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Card(
        modifier = Modifier
            .width(containerW)
            .height(300.dp)
            .padding(4.dp)
            .clickable { fContext.startActivity(DetailsSeriesActivity.newIntent(fContext, serie)) },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            Modifier
                .height(containerH)
                .width(containerW),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 6.dp, start = 6.dp, end = 6.dp)
            ) {
                AsyncImage(
                    model = serie.poster,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier.size(width = imageW, height = imageH),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = serie.nome,
                modifier = Modifier
                    .width(containerW)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp),
                fontSize = 14.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground

            )
        }


    }


}


@Composable
fun SerieItemRow(
    serie: Series,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Box(modifier = Modifier
        .clickable { fContext.startActivity(DetailsSeriesActivity.newIntent(fContext, serie)) }
        .width(140.dp)) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = serie.poster,
                contentDescription = "Series Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 140.dp, height = 200.dp),
                placeholder = painterResource(id = R.drawable.placeholder)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = serie.nome,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SerieItemMore(serie: Series) {
    val fContext = LocalContext.current
    Card(
        modifier = Modifier
            .height(imageH)
            .width(imageW)
            .clickable { fContext.startActivity(DetailsSeriesActivity.newIntent(fContext, serie)) },
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        AsyncImage(
            model = serie.poster,
            contentDescription = "Capa do filme",
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier.size(width = 140.dp, height = 220.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun SerieItemPager(
    serie: Series,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Surface(
        modifier = Modifier
            .size(width = 240.dp, height = 385.dp)
            .clickable { fContext.startActivity(DetailsSeriesActivity.newIntent(fContext, serie)) },
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier.size(width = 240.dp, height = 330.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            AsyncImage(
                model = serie.poster,
                contentDescription = "Imagens dos filmes",
                placeholder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier.width(240.dp)
            )
            Spacer(Modifier.height(4.dp))

            Text(
                text = serie.nome,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                maxLines = 1,

                )
        }
    }
}

@Composable
fun SerieCardItem(
    serie: Series,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
) {
    val fContext = LocalContext.current
    Card(
        modifier
            .clickable { fContext.startActivity(DetailsSeriesActivity.newIntent(fContext, serie)) }
            .fillMaxWidth()
            .heightIn(150.dp),
        elevation = elevation
    ) {
        Column {
            AsyncImage(
                model = serie.poster,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = serie.nome,
                    color = MaterialTheme.colors.onBackground
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val notaFilmes = serie.nota / 2
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = notaFilmes.toString(),
                        fontStyle = FontStyle.Italic
                    )
                    RatingBar(rating = serie.nota / 2)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SerieCardItemPreview() {
    SerieCardItem(serie = sampleSeries[1])
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SerieItemPagerPreview() {
    SerieItemPager(serie = sampleSeries[1])
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SerieItemMorePreview() {
    SerieItemMore(serie = sampleSeries[1])
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SerieItemRowPreview() {
    SerieItemRow(serie = sampleSeries[1])
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SeriesItemGridPreview() {
    SeriesItemGrid(serie = sampleSeries[1])
}




