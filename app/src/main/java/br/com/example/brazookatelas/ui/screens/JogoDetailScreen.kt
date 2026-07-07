@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.VideogameAsset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.example.brazookatelas.model.GamePlatform
import br.com.example.brazookatelas.model.GamerStatus
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.ui.components.BrazookImage
import br.com.example.brazookatelas.ui.components.RatingBar
import br.com.example.brazookatelas.ui.components.badges.AgeRatingBadge
import br.com.example.brazookatelas.ui.components.badges.PlatformBadge
import br.com.example.brazookatelas.ui.components.items.GamerStatusWidget
import br.com.example.brazookatelas.ui.theme.CategoryAccent
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.OverlayTextPrimary
import br.com.example.brazookatelas.ui.theme.StarGold
import br.com.example.brazookatelas.ui.viewmodel.JogosProgressViewModel

/**
 * Tela de detalhes de Jogos — atmosfera de "central gamer": capa com borda que respira em ciano
 * quando o status é "Jogando", seletor de status persistido (`GamerStatusWidget`) e painel
 * "Onde Jogar" com as badges de marca oficiais das plataformas compatíveis.
 * Recebe o modelo [Jogos] concreto (não `MediaItem` genérico) por precisar de `estudio` e
 * `plataformas`, que a interface unificada não expõe.
 */
@Composable
fun JogoDetailScreen(
    jogo: Jogos,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = CategoryAccent(
        color = MaterialTheme.colorScheme.tertiary,
        onColor = MaterialTheme.colorScheme.onTertiary
    )
    val progressViewModel: JogosProgressViewModel = viewModel()
    val status by remember(jogo.id) { progressViewModel.gamerStatusFlow(jogo.id) }
        .collectAsState(initial = GamerStatus.QUERO_JOGAR)

    val platforms = remember(jogo.plataformas) { GamePlatform.parse(jogo.plataformas) }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val infiniteTransition = rememberInfiniteTransition(label = "coverGlow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "coverGlowAlpha"
    )

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp)
                            .let { base ->
                                if (status == GamerStatus.JOGANDO) {
                                    base.border(3.dp, accent.color.copy(alpha = glowAlpha))
                                } else {
                                    base
                                }
                            }
                    ) {
                        BrazookImage(
                            model = jogo.capa,
                            contentDescription = jogo.nome,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        0.0f to Color.Black.copy(alpha = 0.45f),
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
                }

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = jogo.nome,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.VideogameAsset,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${jogo.estudio} · ${jogo.ano}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AgeRatingBadge(classification = jogo.classificacao)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "%.1f".format(jogo.nota),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = StarGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RatingBar(rating = jogo.nota / 2.0, starsColor = StarGold, starSize = 16.dp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    GenreTagRow(genero = jogo.genero)

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Sinopse",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = jogo.sinopse,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            lineHeight = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Status Gamer",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    GamerStatusWidget(
                        status = status,
                        onStatusChange = { newStatus -> progressViewModel.setGamerStatus(jogo.id, newStatus) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Onde Jogar",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(platforms) { platform ->
                            PlatformBadge(platform = platform, onClick = {})
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

/** Gênero exibido como uma única tag colorida — `Jogos.genero` é um valor simples (ex.: "Ação"). */
@Composable
private fun GenreTagRow(genero: String, modifier: Modifier = Modifier) {
    if (genero.isBlank()) return
    val accent = LocalCategoryAccent.current

    Row(modifier = modifier) {
        Surface(
            color = accent.color.copy(alpha = 0.15f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = genero,
                style = MaterialTheme.typography.labelMedium,
                color = accent.color,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}
