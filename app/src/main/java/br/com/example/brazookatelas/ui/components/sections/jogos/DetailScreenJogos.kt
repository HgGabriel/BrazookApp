package br.com.example.brazookatelas.ui.components.sections.jogos

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.gradient
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.sampledata.sampleJogos
import br.com.example.brazookatelas.sampledata.sampleSeries
import br.com.example.brazookatelas.ui.components.sections.filmes.MovieInfo
import coil.compose.AsyncImage


@Composable
fun DetailScreenJogos(
    jogos: Jogos,
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Box(modifier = Modifier.background(brush = gradient)) {

        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xfffbc35b)
                    ),
                    shape = RoundedCornerShape(32.dp),
                    onClick = {
                    },
                ) {
                    Text(text = "Favoritar")
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 16.dp, vertical = 8.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = {
                            onBackPressedDispatcher?.onBackPressed()
                        })
                        {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Detalhes", style = MaterialTheme.typography.h6)
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = jogos.capa,
                            contentDescription = "Movie Image",
                            placeholder = painterResource(id = R.drawable.placeholder),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            jogos.nome, style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(
                                start = 24.dp, end = 24.dp, top = 8.dp, bottom = 24.dp
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            MovieInfo(
                                imageVector = Icons.Default.Games,
                                title = "Gênero",
                                value = jogos.genero
                            )
                            MovieInfo(
                                imageVector = Icons.Default.Face,
                                title = "Classificação",
                                value = jogos.classificacao
                            )
                            MovieInfo(
                                imageVector = Icons.Default.Star,
                                title = "Nota",
                                value = "%.1f/5.0".format(jogos.nota)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(thickness = 4.dp)

                    Text(
                        "Sinopse", style = MaterialTheme.typography.h6, fontSize = 26.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
                    )
                    Text(
                        jogos.sinopse,
                        style = MaterialTheme.typography.body2,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(
                            horizontal = 24.dp
                        )
                    )

                    Text(
                        "Estudio", style = MaterialTheme.typography.h6, fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                    Text(
                        jogos.estudio, style = MaterialTheme.typography.body2, fontSize = 14.sp,
                        modifier = Modifier.padding(
                            horizontal = 24.dp
                        )
                    )

                    Text(
                        "Plataformas", style = MaterialTheme.typography.h6, fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                    Text(
                        jogos.plataformas, style = MaterialTheme.typography.body2, fontSize = 14.sp,
                        modifier = Modifier.padding(
                            horizontal = 24.dp
                        )
                    )

                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)

@Composable
fun DetailScreenJogosPreview() {
    DetailScreenJogos(jogos = sampleJogos[1])
}
