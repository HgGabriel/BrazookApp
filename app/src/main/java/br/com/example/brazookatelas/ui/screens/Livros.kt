@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import br.com.example.brazookatelas.ui.components.items.BookListCard
import br.com.example.brazookatelas.ui.components.items.BookRowItem
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel

@Composable
fun LivrosScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.livrosUiState.collectAsState()
    val query = viewModel.getSearchQuery("livro")

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(
            searchText = query,
            onSearchChange = { viewModel.onSearchQueryChanged("livro", it) }
        )

        AnimatedContent(
            targetState = query.isBlank(),
            transitionSpec = {
                (fadeIn() + slideInVertically { it / 8 }) with
                        (fadeOut() + slideOutVertically { -it / 8 })
            },
            label = "livrosContent"
        ) { isDefault ->
            if (isDefault) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // 1. Carrossel de Capas "Estante Física"
                    item {
                        val popular = items.sortedByDescending { it.rating }.take(6)
                        Column {
                            Text(
                                text = "Mais Lidos & Recomendados",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                items(popular) { book ->
                                    BookRowItem(
                                        item = book,
                                        onClick = { onNavigateToDetails(book.id) }
                                    )
                                }
                            }
                        }
                    }

                    // 2. Lista Vertical de Todos os Livros
                    item {
                        Text(
                            text = "Todos os Livros",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                        )
                    }

                    items(items) { book ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            BookListCard(
                                item = book,
                                onClick = { onNavigateToDetails(book.id) }
                            )
                        }
                    }
                }
            } else {
                // Modo de Busca
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items) { book ->
                        BookListCard(
                            item = book,
                            onClick = { onNavigateToDetails(book.id) }
                        )
                    }
                }
            }
        }
    }
}