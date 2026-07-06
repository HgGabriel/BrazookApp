@file:OptIn(ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    searchText: String,
    onSearchChange: (String) -> Unit,
) {
    TextField(
        value = searchText,
        onValueChange = { newValue ->
            onSearchChange(newValue)
        },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.0f),
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchText.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { onSearchChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Limpar busca",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        placeholder = {
            Text(text = "O que você procura?")
        },
        singleLine = true
    )
}
