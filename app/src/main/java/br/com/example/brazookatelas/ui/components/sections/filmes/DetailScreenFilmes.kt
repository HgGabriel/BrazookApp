package br.com.example.brazookatelas.ui.components.sections.filmes

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.gradient
import br.com.example.brazookatelas.model.Filmes
import coil.compose.AsyncImage


@Composable
fun DetailScreenFilmes(
    filmes: Filmes,
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
                        model = filmes.poster,
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.FillBounds,
                        placeholder = painterResource(id = R.drawable.placeholder),
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
                            value = filmes.genero
                        )
                        MovieInfo(
                            imageVector = Icons.Default.Face,
                            title = "Classificação",
                            value = filmes.classificacao
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

}

@Composable
fun MovieInfo(
    imageVector: ImageVector = Icons.Default.Movie,
    title: String,
    value: String,
) {
    Column(
        modifier = Modifier
            .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(12.dp)
            .size(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.body2,maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

