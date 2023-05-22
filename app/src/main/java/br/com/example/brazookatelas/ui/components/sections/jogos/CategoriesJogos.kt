package br.com.example.brazookatelas.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun CategoriesJogos() {
    val categories = listOf(
        "Ação",
        "Terror",
    )
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(top = 16.dp)) {

        Text(
            text = "Categorias",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 16.dp)
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
                            color = White,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun CategoriesJogosPreview() {
    CategoriesJogos()
}