package br.com.example.brazookatelas.ui.components.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.components.SearchTextField
import br.com.example.brazookatelas.ui.components.items.MediaItemGridCard

@Composable
fun MediaGridSection(
    title: String,
    items: List<MediaItem>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onItemClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTextField(
            searchText = searchQuery,
            onSearchChange = onSearchChange
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
        )

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
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}
