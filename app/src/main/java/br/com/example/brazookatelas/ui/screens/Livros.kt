@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.BookListCard
import br.com.example.brazookatelas.ui.components.items.BookRowItem
import br.com.example.brazookatelas.ui.components.items.VirtualShelf
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.livrosAccent
import br.com.example.brazookatelas.ui.viewmodel.MediaViewModel
import kotlinx.coroutines.delay

@Composable
fun LivrosScreen(
    onNavigateToDetails: (String) -> Unit = {},
    viewModel: MediaViewModel = viewModel()
) {
    val items by viewModel.livrosUiState.collectAsState()
    val query = viewModel.getSearchQuery("livro")
    val accent = livrosAccent()

    CompositionLocalProvider(LocalCategoryAccent provides accent) {
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
                    // 1. Carrossel de Capas "Estante Física" — duas prateleiras físicas empilhadas
                    item {
                        val popular = items.sortedByDescending { it.rating }.take(12)
                        val shelves = popular.chunked(6)
                        Column {
                            Text(
                                text = "Mais Lidos & Recomendados",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                            )
                            shelves.forEachIndexed { shelfIndex, shelfBooks ->
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                                ) {
                                    items(shelfBooks) { book ->
                                        BookRowItem(
                                            item = book,
                                            onClick = { onNavigateToDetails(book.id) }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                VirtualShelf()
                                if (shelfIndex != shelves.lastIndex) {
                                    Spacer(modifier = Modifier.height(20.dp))
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

                    itemsIndexed(items) { index, book ->
                        StaggeredBookListItem(
                            book = book,
                            index = index,
                            onClick = { onNavigateToDetails(book.id) }
                        )
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
}

/**
 * Cartão da lista vertical com entrada escalonada (stagger de 40ms por item) ao rolar —
 * reinicia a animação sempre que a identidade da lista filtrada mudar.
 */
@Composable
private fun StaggeredBookListItem(
    book: MediaItem,
    index: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember(book.id) { mutableStateOf(false) }
    LaunchedEffect(book.id) {
        delay(index * 40L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it / 6 }
    ) {
        Box(modifier = modifier.padding(horizontal = 16.dp)) {
            BookListCard(
                item = book,
                onClick = onClick
            )
        }
    }
}