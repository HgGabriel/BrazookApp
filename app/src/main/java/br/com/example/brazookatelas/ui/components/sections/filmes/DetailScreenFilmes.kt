package br.com.example.brazookatelas.ui.components.sections.filmes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Watch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.example.brazookatelas.model.Filmes
import coil.compose.AsyncImage


@Composable
fun DetailScreenFilmes(
    filmes: Filmes,
) {
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
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Detalhes", style = MaterialTheme.typography.h6)
            }
            Row(
                modifier = Modifier
                    .height(320.dp)
                    .padding(horizontal = 24.dp)
            ) {
                AsyncImage(
                    model = filmes.poster,
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .weight(0.7f)
                        .height(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier
                        .height(320.dp)
                        .weight(0.3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    MovieInfo(
                        imageVector = Icons.Default.Videocam,
                        title = "Gênero",
                        value = filmes.genero
                    )
                    MovieInfo(
                        imageVector = Icons.Default.Watch,
                        title = "Duração",
                        value = "movie.duration"
                    )
                    MovieInfo(
                        imageVector = Icons.Default.Star,
                        title = "Nota",
                        value = "${filmes.nota}/10"
                    )
                }
            }
            Text(
                filmes.filme, style = MaterialTheme.typography.h6,
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
                filmes.sinopse, style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(
                    horizontal = 24.dp, vertical = 16.dp
                )
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun MovieInfo(
    imageVector: ImageVector,
    title: String,
    value: String,
) {
    Column(
        modifier = Modifier
            .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { }
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.subtitle1)
    }
}

