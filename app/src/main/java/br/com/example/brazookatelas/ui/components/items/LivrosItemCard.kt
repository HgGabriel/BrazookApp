package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Livros
import br.com.example.brazookatelas.sampledata.sampleLivros
import br.com.example.brazookatelas.ui.screens.Details.DetailsJogosActivity
import br.com.example.brazookatelas.ui.screens.Details.DetailsLivrosActivity
import coil.compose.AsyncImage
import java.math.BigDecimal

val gradientAmarelo = Brush.linearGradient(
    0.0f to Color(0xff404300),
    0.48f to Color(0xff00350c),
    1.0f to Color(0xff001b2c),
    start = Offset.Zero,
    end = Offset.Infinite
)

val gradientEscuro = Brush.linearGradient(
    0.0f to Color(0xff878d00),
    0.48f to Color(0xff005a15),
    1.0f to Color(0xff07314b),
    start = Offset.Zero,
    end = Offset.Infinite
)
@Composable
fun LivroItemPager(
    livros: Livros,
) {
val fContext = LocalContext.current
    Surface(
        shape = RoundedCornerShape(16.dp), elevation = 8.dp,
        modifier = Modifier.size(height = 240.dp, width = 360.dp) .clickable { fContext.startActivity(
            DetailsLivrosActivity.newIntent(fContext, livros)) }
    ) {
        Box(Modifier.background(brush = gradientAmarelo)) {
            Row {
                Surface(
                    shape = RoundedCornerShape(8.dp), elevation = 4.dp, modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 12.dp, start = 12.dp, bottom = 12.dp, end = 6.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    AsyncImage(
                        model = livros.capa,
                        contentDescription = "Capa do Livro",
                        placeholder = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier.size(width = 140.dp, height = 220.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = livros.nome,
                            style = MaterialTheme.typography.h1,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            fontSize = 30.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Feito por ${livros.autor} (${livros.ano})",
                            style = MaterialTheme.typography.subtitle1,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp), elevation = 4.dp, modifier = Modifier
                            .padding(4.dp)
                            .height(120.dp)
                            .wrapContentHeight()
                    ) {
                        Box(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .align(Alignment.Center),
                                text = livros.sinopse,
                                style = MaterialTheme.typography.subtitle2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                            )
                        }


                    }

                    Column(modifier = Modifier.padding(bottom = 4.dp)) {

                        Text(
                            modifier = Modifier,
                            text = livros.genero,
                            style = MaterialTheme.typography.subtitle1,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.onBackground
                        )

                    }

                }


            }
        }

    }
}


@Composable
fun LivroItemRow(
    livros: Livros,
    modifier: Modifier = Modifier,
) {
    val fContext = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            elevation = 8.dp,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.size(width = 140.dp, height = 200.dp) .clickable { fContext.startActivity(
                DetailsLivrosActivity.newIntent(fContext, livros)) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = livros.capa,
                    contentDescription = "Book Image",
                    modifier = Modifier.size(width = 140.dp, height = 200.dp),
                    placeholder = painterResource(id =R.drawable.placeholder),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.width(100.dp),
            text = livros.nome,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Composable
fun LivroItemList(
    livros: Livros,
) {
    val fContext = LocalContext.current
    Surface(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { fContext.startActivity(
                DetailsLivrosActivity.newIntent(fContext, livros)) },
    ) {
        Box(Modifier.background(brush = gradientEscuro)) {
            Row {
                Surface(
                    shape = RoundedCornerShape(4.dp), elevation = 8.dp, modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 12.dp, start = 12.dp, bottom = 12.dp, end = 6.dp)
                        .align(Alignment.CenterVertically)

                ) {
                    AsyncImage(
                        model = livros.capa,
                        contentDescription = "Capa do Livro",
                        placeholder = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier.size(width = 70.dp, height = 110.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = livros.nome,
                        style = MaterialTheme.typography.h1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 20.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = livros.autor,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        modifier = Modifier,
                        text = livros.genero,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "%.1f".format(livros.nota), fontStyle = FontStyle.Italic)
                        RatingBar(rating = livros.nota)
                    }

                }


            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun LivroItemListPreview() {
    LivroItemList(sampleLivros[1])
}


@Preview(showBackground = true)
@Composable
fun LivroItemRowPreview() {
    LivroItemRow(sampleLivros[1])
}

@Preview(showBackground = true)
@Composable
fun LivroItemPagerPreview() {
    LivroItemPager(sampleLivros[1])
}