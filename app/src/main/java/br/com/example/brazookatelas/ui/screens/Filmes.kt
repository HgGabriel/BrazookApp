@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.MediaItemGridCard
import br.com.example.brazookatelas.ui.components.sections.HeroPager
import br.com.example.brazookatelas.ui.components.sections.MediaRow
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel

@Composable
fun FilmesScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.filmesUiState.collectAsState()
    val query = viewModel.getSearchQuery("filme")

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
                    // 1. Hero Pager para "Destaques / Em alta" (ordena por ano)
                    item {
                        val trending = items.sortedByDescending { it.year }.take(5)
                        HeroPager(
                            items = trending,
                            onItemClick = { onNavigateToDetails(it.id) }
                        )
                    }

                    // 2. Recomendados (ordena por nota)
                    item {
                        val recommended = items.sortedByDescending { it.rating }
                        MediaRow(
                            title = "Recomendados",
                            items = recommended,
                            onItemClick = { onNavigateToDetails(it.id) },
                            onSeeAllClick = {}
                        )
                    }

                    // 3. Grid de outros títulos
                    item {
                        Text(
                            text = "Todos os Filmes",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                        )
                    }

                    item {
                        val remaining = items.drop(5)
                        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                            remaining.chunked(2).forEach { rowItems ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    rowItems.forEach { item ->
                                        MediaItemGridCard(
                                            item = item,
                                            onClick = { onNavigateToDetails(item.id) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
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