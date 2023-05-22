package br.com.example.brazookatelas.ui.components.sections.Series

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesSeries() {
    val categories = listOf(
        "Animação",
        "Terror",
        "Ação",
        "Comédia",
        "Romance",
        "Ficção",
        "Drama",
        "Aventura",
    )
    val scrollState = rememberScrollState()

    Column {
        Text(
            text = "Categorias",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(16.dp)
        ) {
            repeat(categories.size) { index ->
                Surface(
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) 24.dp else 0.dp,
                            end = 12.dp,
                        )
                        .border(width = 2.dp, color = Color(0xff004618),shape = RoundedCornerShape(16.dp))
                        .clickable { },
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp


                ) {
                    Box(modifier = Modifier

                        .background(Color(0xff008e31))
                        .wrapContentSize()

                    ) {
                        Text(
                            text = categories[index],
                            fontSize = 14.sp,
                            color = Color.White,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    CategoriesSeries()
}