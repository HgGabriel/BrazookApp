@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.SportsEsports
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.BlurryHeaderCard
import br.com.example.brazookatelas.ui.components.items.MediaItemGridCard
import br.com.example.brazookatelas.ui.theme.CategoryAccent
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel
import kotlinx.coroutines.delay

@Composable
fun JogosScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.jogosUiState.collectAsState()
    val query = viewModel.getSearchQuery("jogo")
    var selectedPlatform by remember { mutableStateOf<String?>(null) }

    val accent = CategoryAccent(
        color = MaterialTheme.colorScheme.tertiary,
        onColor = MaterialTheme.colorScheme.onTertiary
    )

    // Contagem por plataforma (via `Jogos.badgesList`, já normalizado por `GamePlatform.parse`)
    // para exibir "PS4 · 6" no próprio chip do filtro, sem custo adicional de modelo.
    val platformCounts = remember(items) {
        items.flatMap { it.badgesList }.groupingBy { it }.eachCount()
    }

    val filteredItems = remember(items, selectedPlatform) {
        val platform = selectedPlatform
        if (platform == null) items else items.filter { platform in it.badgesList }
    }

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                searchText = query,
                onSearchChange = { viewModel.onSearchQueryChanged("jogo", it) }
            )

            AnimatedContent(
                targetState = query.isBlank(),
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 8 }) with
                            (fadeOut() + slideOutVertically { -it / 8 })
                },
                label = "jogosContent"
            ) { isDefault ->
                if (isDefault) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        // 1. Filtro rápido por plataforma
                        item {
                            PlatformFilterRow(
                                platformCounts = platformCounts,
                                selectedPlatform = selectedPlatform,
                                onPlatformSelected = { platform ->
                                    selectedPlatform = if (selectedPlatform == platform) null else platform
                                }
                            )
                        }

                        // 2. Destaque do Mês: BlurryHeaderCard (maior nota do catálogo filtrado)
                        item {
                            AnimatedContent(
                                targetState = selectedPlatform,
                                transitionSpec = { fadeIn() with fadeOut() },
                                label = "featuredFilter"
                            ) { _ ->
                                val featured = filteredItems.maxByOrNull { it.rating }
                                if (featured != null) {
                                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                        Text(
                                            text = "Destaque do Mês",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.padding(bottom = 12.dp)
                                        )
                                        BlurryHeaderCard(
                                            item = featured,
                                            onClick = { onNavigateToDetails(featured.id) }
                                        )
                                    }
                                }
                            }
                        }

                        // 3. Título de Seção
                        item {
                            Text(
                                text = "Todos os Jogos",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                        }

                        // 4. Grid dos Jogos (com brilho neon sutil na borda e entrada escalonada)
                        item {
                            AnimatedContent(
                                targetState = selectedPlatform,
                                transitionSpec = { fadeIn() with fadeOut() },
                                label = "gridFilter"
                            ) { _ ->
                                StaggeredGamesGrid(
                                    items = filteredItems,
                                    onItemClick = { onNavigateToDetails(it.id) }
                                )
                            }
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
                                detailsIcon = Icons.Rounded.SportsEsports
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Fileira de chips de filtro por plataforma, com contagem de jogos entre parênteses. Usa o
 * acento neutro da categoria (`LocalCategoryAccent`, ciano de Jogos) — as cores oficiais de
 * marca (Sony/Microsoft/Nintendo) ficam reservadas exclusivamente para `PlatformBadge` no
 * painel "Onde Jogar" da tela de detalhes (ver `motor_design.md`, item 1).
 */
@Composable
private fun PlatformFilterRow(
    platformCounts: Map<String, Int>,
    selectedPlatform: String?,
    onPlatformSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (platformCounts.isEmpty()) return
    val accent = LocalCategoryAccent.current

    // Ordem fixa e estável (não alfabética) para as plataformas mais relevantes aparecerem primeiro.
    val orderedPlatforms = remember(platformCounts) {
        val priority = listOf("PC", "PlayStation", "Xbox", "Nintendo Switch", "Mobile")
        platformCounts.keys.sortedBy { platform ->
            val index = priority.indexOf(platform)
            if (index == -1) priority.size else index
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(orderedPlatforms) { platform ->
            val isSelected = platform == selectedPlatform
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) accent.color else MaterialTheme.colorScheme.surfaceVariant,
                label = "platformChipBackground"
            )
            val contentColor = if (isSelected) accent.onColor else MaterialTheme.colorScheme.onSurfaceVariant

            Surface(
                color = backgroundColor,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .heightIn(min = 48.dp)
                    .clickable { onPlatformSelected(platform) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "$platform · ${platformCounts[platform] ?: 0}",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        color = contentColor
                    )
                }
            }
        }
    }
}

/**
 * Grid 2 colunas com entrada escalonada (30ms por linha) e um contorno neon sutil derivado do
 * acento da categoria, simulando o "brilho interno na borda" do dashboard gamer sem alterar o
 * `MediaItemGridCard` genérico compartilhado com as demais categorias.
 */
@Composable
private fun StaggeredGamesGrid(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = LocalCategoryAccent.current

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
                            detailsIcon = Icons.Rounded.SportsEsports,
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, accent.color.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
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
