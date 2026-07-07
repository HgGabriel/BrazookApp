@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.model.Series
import br.com.example.brazookatelas.ui.components.BrazookImage
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.MediaItemGridCard
import br.com.example.brazookatelas.ui.components.sections.HeroPager
import br.com.example.brazookatelas.ui.components.sections.MediaRow
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.seriesAccent
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel
import kotlinx.coroutines.delay

@Composable
fun SeriesScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.seriesUiState.collectAsState()
    val query = viewModel.getSearchQuery("serie")
    val accent = seriesAccent()

    // As instâncias vêm de `sampleSeries` sem conversão — o downcast é seguro e dá acesso aos
    // campos concretos (`episodios`, `temporadas`) que a interface MediaItem não expõe.
    val seriesItems = remember(items) { items.mapNotNull { it as? Series } }

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                searchText = query,
                onSearchChange = { viewModel.onSearchQueryChanged("serie", it) }
            )

            AnimatedContent(
                targetState = query.isBlank(),
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 8 }) with
                            (fadeOut() + slideOutVertically { -it / 8 })
                },
                label = "seriesContent"
            ) { isDefault ->
                if (isDefault) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        // 1. Hero Pager: Séries em Destaque (ordena por ano)
                        item {
                            val trending = items.sortedByDescending { it.year }.take(5)
                            HeroPager(
                                items = trending,
                                onItemClick = { onNavigateToDetails(it.id) },
                                badgeLabel = "Em Alta"
                            )
                        }

                        // 2. Para Maratonar (séries com menor tempo estimado de maratona)
                        item {
                            ParaMaratonarSection(
                                series = seriesItems,
                                onItemClick = { onNavigateToDetails(it.id) }
                            )
                        }

                        // 3. Recomendados (ordena por nota)
                        item {
                            val recommended = items.sortedByDescending { it.rating }
                            MediaRow(
                                title = "Séries Recomendadas",
                                items = recommended,
                                onItemClick = { onNavigateToDetails(it.id) },
                                onSeeAllClick = {}
                            )
                        }

                        // 4. Grid das Séries Restantes
                        item {
                            Text(
                                text = "Todas as Séries",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                        }

                        item {
                            val remaining = items.drop(5)
                            StaggeredSeriesGrid(
                                items = remaining,
                                onItemClick = { onNavigateToDetails(it.id) }
                            )
                        }
                    }
                } else {
                    // Modo de Busca
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(items) { item ->
                            MediaItemGridCard(
                                item = item,
                                onClick = { onNavigateToDetails(item.id) },
                                detailsIcon = Icons.Rounded.Tv
                            )
                        }
                    }
                }
            }
        }
    }
}

/** `episodios` já é o total da série inteira (ver Series.kt) — multiplicar por `temporadas` de novo dobraria a contagem. */
private fun estimatedMarathonMinutes(serie: Series): Int = serie.episodios * 45

private fun formatMarathonTime(minutes: Int): String {
    val hours = minutes / 60
    val remainder = minutes % 60
    return if (remainder == 0) "Maratona estimada: ${hours}h" else "Maratona estimada: ${hours}h ${remainder}min"
}

@Composable
private fun ParaMaratonarSection(
    series: List<Series>,
    onItemClick: (Series) -> Unit,
    modifier: Modifier = Modifier
) {
    if (series.isEmpty()) return
    val quickBinges = remember(series) {
        series.sortedBy { estimatedMarathonMinutes(it) }.take(5)
    }

    Column(modifier = modifier) {
        Text(
            text = "Para Maratonar",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(quickBinges) { serie ->
                MaratonaCard(serie = serie, onClick = { onItemClick(serie) })
            }
        }
    }
}

@Composable
private fun MaratonaCard(
    serie: Series,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = LocalCategoryAccent.current

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    BrazookImage(
                        model = serie.poster,
                        contentDescription = serie.nome,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = serie.nome,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = formatMarathonTime(estimatedMarathonMinutes(serie)),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = accent.color
                    )
                }
            }

            Surface(
                color = accent.color.copy(alpha = 0.9f),
                shape = RoundedCornerShape(bottomStart = 12.dp),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocalFireDepartment,
                    contentDescription = "Alta Relevância",
                    tint = accent.onColor,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(16.dp)
                )
            }
        }
    }
}

/**
 * Grid 2 colunas com entrada escalonada: cada linha aparece com um delay progressivo de 30ms,
 * reiniciando a animação sempre que a identidade da lista mudar.
 */
@Composable
private fun StaggeredSeriesGrid(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        items.chunked(2).forEachIndexed { rowIndex, rowItems ->
            var rowVisible by remember(items) { mutableStateOf(false) }
            LaunchedEffect(items) {
                delay(rowIndex * 30L)
                rowVisible = true
            }

            AnimatedVisibility(
                visible = rowVisible,
                enter = fadeIn() + slideInVertically { it / 6 }
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        MediaItemGridCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            modifier = Modifier.weight(1f),
                            detailsIcon = Icons.Rounded.Tv
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
