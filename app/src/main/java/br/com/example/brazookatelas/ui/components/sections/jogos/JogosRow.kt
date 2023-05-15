import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.Jogos
import br.com.example.brazookatelas.sampledata.sampleJogos
import br.com.example.brazookatelas.ui.components.items.JogoItemPager

@Composable
fun JogosRowTrend(
    title: String,
    jogos: List<Jogos>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            TextButton(onClick = { }) {
                Text(text = "Ver todos")
            }
        }

        LazyRow(
            Modifier
                .padding(
                    top = 8.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(jogos) { j ->
                JogoItemPager(jogos = j)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JogosRowTrendPreview() {
    JogosRowTrend(title = "Em alta", jogos = sampleJogos)
}