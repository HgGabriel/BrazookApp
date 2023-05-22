package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.sampledata.sampleJogos
import coil.compose.AsyncImage
import java.math.BigDecimal

val notaJogos = BigDecimal((4..5).random()).setScale(2).toDouble()

@Composable
fun JogoItemPager(
    jogos: Jogos,
    modifier: Modifier = Modifier,
) {
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentSize()
            .size(height = 200.dp, width = 300.dp),

        ) {
        Box() {
            AsyncImage(
                model = jogos.capa,
                contentDescription = "Capa do jogo",
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.fobia),
                modifier = Modifier
                    .size(height = 250.dp, width = 350.dp)
                    .blur(
                        radiusX = 20.dp,
                        radiusY = 20.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(16.dp))
                    ),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()
                    .apply { setToScale(0.4f, 0.4f, 0.4f, 1f) }),
            )
        }

        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 12.dp),
                    elevation = 4.dp,
                ) {
                    AsyncImage(
                        model = jogos.capa,
                        contentDescription = "Capa do jogo",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.fobia),
                        modifier = Modifier.size(100.dp),
                    )

                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    val idade = jogos.classificacao
                    val idadeIcon = when (idade) {
                        "L" -> painterResource(id = R.drawable.idadelivre)
                        "10" -> painterResource(id = R.drawable.idadedez)
                        "12" -> painterResource(id = R.drawable.idadedoze)
                        "14" -> painterResource(id = R.drawable.idadequatorze)
                        "16" -> painterResource(id = R.drawable.idadedezesseis)
                        "18" -> painterResource(id = R.drawable.idadedezoito)
                        else -> {
                            painterResource(id = R.drawable.placeholder)
                        }
                    }

                    Image(
                        idadeIcon,
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )

                    Text(
                        text = jogos.genero,
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 12.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = notaJogos.toString(),
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }


            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = jogos.nome,
                    color = Color.White,
                    style = MaterialTheme.typography.h1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 26.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Feito por ${jogos.estudio} (${jogos.ano})",
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )

                Surface(
                    shape = RoundedCornerShape(8.dp), elevation = 4.dp, modifier = Modifier
                        .height(120.dp)
                        .wrapContentHeight()
                ) {
                    Box(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .align(Alignment.Center),
                            text = jogos.sinopse,
                            style = MaterialTheme.typography.subtitle2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                        )
                    }


                }
            }

        }

    }
}

@Composable
fun JogoItemRecom(jogos: Jogos) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            elevation = 8.dp,
            shape = RoundedCornerShape(6.dp), modifier = Modifier.size(
                width = 240.dp,
                height = 360.dp
            )
        ) {
            Box {
                AsyncImage(
                    model = jogos.capa,
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
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix()
                        .apply { setToScale(0.4f, 0.4f, 0.4f, 1f) }),
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
                    AsyncImage(
                        model = jogos.capa,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        placeholder = painterResource(id = R.drawable.fobia),

                        )
                }

                Text(
                    text = "${jogos.nome} \n (${jogos.ano})",
                    color = Color.White,
                    style = MaterialTheme.typography.h4,
                    maxLines = 3,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(150.dp)
                        .padding(bottom = 4.dp)
                )
                Surface(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
                        val idade = jogos.classificacao
                        val idadeIcon = when (idade) {
                            "L" -> painterResource(id = R.drawable.idadelivre)
                            "10" -> painterResource(id = R.drawable.idadedez)
                            "12" -> painterResource(id = R.drawable.idadedoze)
                            "14" -> painterResource(id = R.drawable.idadequatorze)
                            "16" -> painterResource(id = R.drawable.idadedezesseis)
                            "18" -> painterResource(id = R.drawable.idadedezoito)
                            else -> {
                                painterResource(id = R.drawable.placeholder)
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize()
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(text = notaJogos.toString(), fontStyle = FontStyle.Italic)
                                RatingBar(rating = notaJogos)
                            }

                            Text(
                                text = jogos.genero,
                                color = MaterialTheme.colors.onBackground,
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = jogos.plataformas,
                                color = MaterialTheme.colors.onBackground,
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                fontSize = 16.sp,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun JogoItemList(jogos: Jogos,) {

    Surface(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
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
                        model = jogos.capa,
                        contentDescription = "Capa do Livro",
                        placeholder = painterResource(id = R.drawable.fobia),
                        modifier = Modifier.size(width = 100.dp, height = 100.dp),
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
                        text = jogos.nome,
                        style = MaterialTheme.typography.h1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 20.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = jogos.estudio,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        modifier = Modifier,
                        text = jogos.genero,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = notaJogos.toString(), fontStyle = FontStyle.Italic)
                        RatingBar(rating = notaJogos)
                    }

                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoItemOutroPreview() {
    JogoItemList(sampleJogos[16])
}


@Preview(showBackground = true)
@Composable
fun JogoItemRecomPreview() {
    JogoItemRecom(sampleJogos[16])
}

@Preview(showBackground = true)
@Composable
fun JogoItemPagerPreview() {
    JogoItemPager(sampleJogos[16])
}


