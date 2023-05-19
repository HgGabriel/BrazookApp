package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme
import coil.compose.AsyncImage
import kotlin.math.ceil
import kotlin.math.floor

val imageH = 260.dp
val imageW = 180.dp

val containerH = 270.dp
val containerW = 190.dp

@Composable
fun FilmeItemGrid(
    filme: Filmes,
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = Modifier
            .width(containerW)
            .height(300.dp)
            .padding(4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xFF043568)
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
                    model = filme.poster,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.mundoluna),
                    modifier = Modifier.size(width = imageW, height = imageH),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = filme.filme,
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
fun FilmeItemRow(
    filme: Filmes,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier
        .clickable { }
        .width(140.dp)) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = filme.poster,
                contentDescription = "Movie Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 140.dp, height = 200.dp),
                placeholder = painterResource(id = R.drawable.mundoluna)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = filme.filme,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FilmeItemMore(filme: Filmes) {
    Card(
        modifier = Modifier
            .height(imageH)
            .width(imageW),
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        AsyncImage(
            model = filme.poster,
            contentDescription = "Capa do filme",
            placeholder = painterResource(id = R.drawable.mundoluna),
            modifier = Modifier.size(width = 140.dp, height = 220.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun FilmeItemPager(
    filme: Filmes,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = Modifier
            .size(width = 240.dp, height = 385.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier.size(width = 240.dp, height = 330.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            AsyncImage(
                model = filme.poster,
                contentDescription = "Imagens dos filmes",
                placeholder = painterResource(id = R.drawable.mundoluna),
                modifier = Modifier.width(240.dp)
            )
            Spacer(Modifier.height(4.dp))

            Text(
                text = filme.filme,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                maxLines = 1,

                )
        }
    }
}

@Composable
fun FilmeCardItem(
    filme: Filmes,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
) {
    Card(
        modifier
            .clickable {

            }
            .fillMaxWidth()
            .heightIn(150.dp),
        elevation = elevation
    ) {
        Column {
            AsyncImage(
                model = filme.poster,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                placeholder = painterResource(id = R.drawable.mundoluna),
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
                    text = filme.filme,
                    color = MaterialTheme.colors.onBackground
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val notaFilmes = filme.nota / 2
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = notaFilmes.toString(),
                        fontStyle = FontStyle.Italic
                    )
                    RatingBar(rating = filme.nota / 2)
                }

            }


        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 2.5)
}

@Preview
@Composable
fun TenStarsRatingPreview() {
    RatingBar(stars = 10, rating = 8.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    RatingBar(rating = 5.0)
}

@Preview
@Composable
fun RatingPreviewWorst() {
    RatingBar(rating = 1.0)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBar(rating = 0.0, starsColor = Color.Gray)
}

@Preview(showBackground = true)
@Composable
fun FilmeItemMorePreview() {
    FilmeItemMore(sampleFilmes[1])
}

@Preview
@Composable
fun FilmeCardItemPreview() {
    FilmeCardItem(
        sampleFilmes[1]
    )
}

@Preview(showBackground = true)
@Composable
fun FilmeItemPagerPreview() {
    FilmeItemPager(
        sampleFilmes[1]
    )
}

@Preview(showBackground = true)
@Composable
fun FilmeItemRowPreview() {
    FilmeItemRow(
        sampleFilmes[1]
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun FilmeItemGridPreview() {
    BrazookaTelasTheme {
        Surface {
            FilmeItemGrid(
                sampleFilmes[1]
            )
        }
    }

}