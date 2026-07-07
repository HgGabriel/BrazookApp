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
import br.com.example.brazookatelas.ui.components.items.MediaItemGridCard
import br.com.example.brazookatelas.ui.components.sections.HeroPager
import br.com.example.brazookatelas.ui.components.sections.MediaRow
import br.com.example.brazookatelas.ui.theme.CategoryAccent
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel
import kotlinx.coroutines.delay

@Composable
fun FilmesScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.filmesUiState.collectAsState()
    val query = viewModel.getSearchQuery("filme")
    var selectedGenre by remember { mutableStateOf<String?>(null) }

    val accent = CategoryAccent(
        color = MaterialTheme.colorScheme.secondary,
        onColor = MaterialTheme.colorScheme.onSecondary
    )

    val genres = remember(items) {
        items.flatMap { it.genre.split("/") }
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
    }

    val filteredItems = remember(items, selectedGenre) {
        val genre = selectedGenre
        if (genre == null) {
            items
        } else {
            items.filter { item ->
                item.genre.split("/").map { it.trim() }.contains(genre)
            }
        }
    }

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                searchText = query,
                onSearchChange = { viewModel.onSearchQueryChanged("filme", it) }
            )

            AnimatedContent(
                targetState = query.isBlank(),
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 8 }) with
                            (fadeOut() + slideOutVertically { -it / 8 })
                },
                label = "filmesContent"
            ) { isDefault ->
                if (isDefault) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        // 1. Filtro rápido por gênero
                        item {
                            GenreFilterRow(
                                genres = genres,
                                selectedGenre = selectedGenre,
                                onGenreSelected = { genre ->
                                    selectedGenre = if (selectedGenre == genre) null else genre
                                }
                            )
                        }

                        // 2. Hero Pager para "Destaques / Em alta" (ordena por ano)
                        item {
                            AnimatedContent(
                                targetState = selectedGenre,
                                transitionSpec = { fadeIn() with fadeOut() },
                                label = "heroFilter"
                            ) { _ ->
                                val trending = filteredItems.sortedByDescending { it.year }.take(5)
                                HeroPager(
                                    items = trending,
                                    onItemClick = { onNavigateToDetails(it.id) },
                                    badgeLabel = "Destaque"
                                )
                            }
                        }

                        // 3. Recomendados (ordena por nota)
                        item {
                            AnimatedContent(
                                targetState = selectedGenre,
                                transitionSpec = { fadeIn() with fadeOut() },
                                label = "recommendedFilter"
                            ) { _ ->
                                val recommended = filteredItems.sortedByDescending { it.rating }
                                MediaRow(
                                    title = "Recomendados",
                                    items = recommended,
                                    onItemClick = { onNavigateToDetails(it.id) },
                                    onSeeAllClick = {}
                                )
                            }
                        }

                        // 4. Grid de outros títulos
                        item {
                            Text(
                                text = "Todos os Filmes",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                        }

                        item {
                            AnimatedContent(
                                targetState = selectedGenre,
                                transitionSpec = { fadeIn() with fadeOut() },
                                label = "gridFilter"
                            ) { _ ->
                                val remaining = filteredItems.drop(5)
                                StaggeredMovieGrid(
                                    items = remaining,
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
                                onClick = { onNavigateToDetails(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GenreFilterRow(
    genres: List<String>,
    selectedGenre: String?,
    onGenreSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (genres.isEmpty()) return
    val accent = LocalCategoryAccent.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(genres) { genre ->
            val isSelected = genre == selectedGenre
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) accent.color else MaterialTheme.colorScheme.surfaceVariant,
                label = "genreChipBackground"
            )
            val contentColor = if (isSelected) accent.onColor else MaterialTheme.colorScheme.onSurfaceVariant

            Surface(
                color = backgroundColor,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .heightIn(min = 48.dp)
                    .clickable { onGenreSelected(genre) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        color = contentColor
                    )
                }
            }
        }
    }
}

/**
 * Grid 2 colunas com entrada escalonada: cada linha aparece com um delay progressivo de 35ms,
 * reiniciando a animação sempre que a lista filtrada (por gênero) mudar de identidade.
 */
@Composable
private fun StaggeredMovieGrid(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        items.chunked(2).forEachIndexed { rowIndex, rowItems ->
            var rowVisible by remember(items) { mutableStateOf(false) }
            LaunchedEffect(items) {
                delay(rowIndex * 35L)
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
                            modifier = Modifier.weight(1f)
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
