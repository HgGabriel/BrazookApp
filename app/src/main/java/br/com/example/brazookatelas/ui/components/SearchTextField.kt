package br.com.example.brazookatelas.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    searchText: String,
    onSearchChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { newValue ->
            onSearchChange(newValue)
        },
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Green,
            unfocusedBorderColor = Color(0xff004618)
        ),
        shape = RoundedCornerShape(percent = 100),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Ícone de Busca")
        },
        label = {
            Text(text = "Pesquisar")
        },
        placeholder = {
            Text(text = "O que você procura?")
        },
        singleLine = true
    )

}
