package br.com.example.brazookatelas.ui.components.sections.series

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.gradient
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.ui.components.sections.filmes.MovieInfo
import coil.compose.AsyncImage


@Composable
fun DetailScreenSeries(
    series: Series,
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
                        backgroundColor = Yellow
                    ),
                    shape = RoundedCornerShape(32.dp),
                    onClick = {
                    },
                ) {
                    Text(text = "Favoritar")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
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
                Row(
                    modifier = Modifier
                        .height(350.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    AsyncImage(
                        model = series.poster,
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .weight(0.7f)
                            .height(350.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Column(
                        modifier = Modifier
                            .height(350.dp)
                            .weight(0.3f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        MovieInfo(
                            imageVector = Icons.Default.Videocam,
                            title = "Gênero",
                            value = series.genero
                        )
                        MovieInfo(
                            imageVector = Icons.Default.Face,
                            title = "Classificação",
                            value = series.classificacao
                        )
                        MovieInfo(
                            imageVector = Icons.Default.Star,
                            title = "Nota",
                            value = "${series.nota}/10"
                        )
                    }
                }
                Text(
                    series.nome, style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(
                        horizontal = 24.dp, vertical = 16.dp
                    )
                )
                Text(
                    "Sinopse", style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    )
                )
                Text(
                    series.sinopse, style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        horizontal = 24.dp, vertical = 16.dp
                    )
                )
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }

}

