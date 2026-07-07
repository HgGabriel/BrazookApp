@file:OptIn(ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.model.Filmes
import br.com.example.brazookatelas.ui.components.BrazookImage
import br.com.example.brazookatelas.ui.components.RatingBar
import br.com.example.brazookatelas.ui.components.badges.AgeRatingBadge
import br.com.example.brazookatelas.ui.components.items.FichaTecnicaWidget
import br.com.example.brazookatelas.ui.components.items.PlayButtonFAB
import br.com.example.brazookatelas.ui.theme.CategoryAccent
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.OverlayTextPrimary
import br.com.example.brazookatelas.ui.theme.StarGold
import kotlinx.coroutines.launch

/**
 * Tela de detalhes de Filmes — atmosfera de "sala de cinema": pôster em destaque com
 * overlay escuro, botão de play flutuante sobreposto e ficha técnica expansível.
 * Diferente de [MediaDetailScreen] (genérica), esta tela recebe o modelo [Filmes] concreto
 * porque precisa de campos que a interface MediaItem não expõe (atores, diretores, roteiristas).
 */
@Composable
fun FilmeDetailScreen(
    filme: Filmes,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = CategoryAccent(
        color = MaterialTheme.colorScheme.secondary,
        onColor = MaterialTheme.colorScheme.onSecondary
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        snackbarData = data
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + slideInVertically { it / 6 }
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(420.dp)
                        ) {
                            BrazookImage(
                                model = filme.poster,
                                contentDescription = filme.filme,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Camada de fumaça escura para atmosfera de sala de cinema
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            0.0f to Color.Black.copy(alpha = 0.55f),
                                            0.4f to Color.Transparent,
                                            0.75f to Color.Black.copy(alpha = 0.3f),
                                            1.0f to MaterialTheme.colorScheme.background
                                        )
                                    )
                            )

                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(12.dp)
                                    .size(40.dp)
                                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = "Voltar",
                                    tint = OverlayTextPrimary
                                )
                            }
                        }

                        PlayButtonFAB(
                            onPlayTrailer = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Reproduzindo trailer oficial de ${filme.filme}..."
                                    )
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 24.dp)
                                .offset(y = 28.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = filme.filme,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${filme.genero}  •  ${filme.ano}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        AgeRatingBadge(classification = filme.classificacao)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "%.1f/10".format(filme.nota),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = StarGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RatingBar(rating = filme.nota / 2.0, starsColor = StarGold, starSize = 16.dp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Sinopse",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = filme.sinopse,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            lineHeight = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    FichaTecnicaWidget(filme = filme)

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
