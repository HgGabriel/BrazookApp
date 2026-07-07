@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Schedule
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
import br.com.example.brazookatelas.model.Livros
import br.com.example.brazookatelas.ui.components.BrazookImage
import br.com.example.brazookatelas.ui.components.RatingBar
import br.com.example.brazookatelas.ui.components.badges.AudienceBadge
import br.com.example.brazookatelas.ui.components.items.AuthorProfileCard
import br.com.example.brazookatelas.ui.components.items.ReadingTracker
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.OverlayTextPrimary
import br.com.example.brazookatelas.ui.theme.StarGold
import br.com.example.brazookatelas.ui.theme.livrosAccent
import br.com.example.brazookatelas.ui.viewmodel.LivrosProgressViewModel

/** Média nacional de 2 minutos por página (ver `livros.md`, seção "Estimativa de Tempo de Leitura"). */
private const val MINUTES_PER_PAGE = 2

private fun formatReadingTime(paginas: Int): String {
    val totalMinutes = paginas * MINUTES_PER_PAGE
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return when {
        hours > 0 && minutes > 0 -> "~${hours}h ${minutes}min"
        hours > 0 -> "~${hours}h"
        else -> "~${minutes}min"
    }
}

/**
 * Tela de detalhes de Livros — atmosfera de "biblioteca aconchegante": capa em destaque,
 * rastreador de leitura interativo (`ReadingTracker`) com progresso persistido e ficha do autor.
 * Recebe o modelo [Livros] concreto (não `MediaItem` genérico) por precisar de `paginas` e `autor`.
 */
@Composable
fun LivrosDetailScreen(
    livro: Livros,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = livrosAccent()
    val progressViewModel: LivrosProgressViewModel = viewModel()
    val pagesRead by remember(livro.id) { progressViewModel.readingProgressFlow(livro.id) }
        .collectAsState(initial = 0)

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

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
                    ) {
                        BrazookImage(
                            model = livro.capa,
                            contentDescription = livro.nome,
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
                        text = livro.nome,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${livro.autor} · ${livro.ano}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AudienceBadge(alvo = livro.alvo)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "%.1f".format(livro.nota),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = StarGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RatingBar(rating = livro.nota / 2.0, starsColor = StarGold, starSize = 16.dp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    GenreTagsRow(genero = livro.genero)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Leitura estimada: ${formatReadingTime(livro.paginas)} (ritmo médio)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Sinopse",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = livro.sinopse,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                            lineHeight = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Progresso de Leitura",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ReadingTracker(
                        totalPaginas = livro.paginas,
                        pagesRead = pagesRead,
                        onPagesReadChange = { progressViewModel.setReadingProgress(livro.id, it) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AuthorProfileCard(autor = livro.autor, genero = livro.genero)

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

/** Gêneros separados por vírgula no mock, mapeados como tags de bordas arredondadas e preenchimento sutil. */
@Composable
private fun GenreTagsRow(genero: String, modifier: Modifier = Modifier) {
    val tags = remember(genero) {
        genero.split(",").map { it.trim() }.filter { it.isNotBlank() }
    }
    if (tags.isEmpty()) return

    Row(modifier = modifier) {
        tags.forEachIndexed { index, tag ->
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = tag,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            if (index != tags.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
