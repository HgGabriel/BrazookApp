package br.com.example.brazookatelas.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesJogos() {
    val categories = listOf(
        "Ação",
        "Terror",
    )
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(top = 16.dp)) {

        Text(text = "Categorias", style = MaterialTheme.typography.h6, modifier = Modifier.padding(start = 16.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(16.dp)
        ) {
            repeat(categories.size) { index ->
                Surface(
                    /// order matters
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) 24.dp else 0.dp,
                            end = 12.dp,
                        )
                        .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { }
                        .padding(12.dp)
                ) {
                    Text(text = categories[index], style = MaterialTheme.typography.caption)
                }
            }
        }
    }




}

@Preview(showBackground = true)
@Composable
fun CategoriesJogosPreview() {
    CategoriesJogos()
}