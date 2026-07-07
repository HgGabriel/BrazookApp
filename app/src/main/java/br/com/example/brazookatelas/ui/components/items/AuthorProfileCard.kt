package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import java.text.Normalizer

private fun normalizeGenero(raw: String): String {
    val stripped = Normalizer.normalize(raw, Normalizer.Form.NFD).replace(Regex("\\p{M}"), "")
    return stripped.trim().lowercase()
}

/**
 * Biografia é 100% texto fixo por gênero literário — não há campo de "estilo de escrita"
 * distinto de `Livros.genero` no modelo, então a variação é apenas entre gêneros, nunca
 * entre livros do mesmo gênero. `genero` no mock é uma string livre e às vezes com erros de
 * digitação ("Ficcção", "Romace"); por isso o casamento é por `contains` sobre o token
 * normalizado, não por igualdade exata.
 */
private val biosByGenre = listOf(
    listOf("romance") to "Autor(a) dedicado(a) a histórias de afeto e conflito humano, explorando relações e escolhas que definem quem somos.",
    listOf("drama") to "Autor(a) reconhecido(a) por narrativas densas, que mergulham nas dores e superações da vida cotidiana.",
    listOf("terror", "crime") to "Autor(a) especialista em construir tensão e suspense, guiando o leitor por tramas sombrias e imprevisíveis.",
    listOf("clássic", "classic") to "Autor(a) de obras que atravessaram gerações, hoje referências essenciais da literatura de língua portuguesa.",
    listOf("poe") to "Autor(a) que trabalha a palavra em sua forma mais condensada, buscando ritmo e imagem antes da narrativa.",
    listOf("fantasi") to "Autor(a) que constrói mundos e regras próprias, convidando o leitor a escapar para universos imaginativos.",
    listOf("ficç", "ficc", "realismo") to "Autor(a) que mistura observação aguçada da realidade com um olhar criativo e original.",
    listOf("queer") to "Autor(a) que dá voz a experiências e afetos plurais, com sensibilidade para o que foge do convencional."
)

private fun bioForGenero(genero: String): String {
    val primaryToken = normalizeGenero(genero.split(",").first())
    return biosByGenre.firstOrNull { (keywords, _) -> keywords.any { primaryToken.contains(it) } }?.second
        ?: "Autor(a) brasileiro(a) cuja obra explora temas marcantes com uma voz literária própria."
}

/**
 * Seção "Sobre o Autor" — avatar circular com iniciais em tons terrosos e biografia curta
 * derivada do gênero principal do livro (ver [bioForGenero]).
 */
@Composable
fun AuthorProfileCard(autor: String, genero: String, modifier: Modifier = Modifier) {
    val accent = LocalCategoryAccent.current
    val bio = remember(genero) { bioForGenero(genero) }
    val initials = remember(autor) {
        autor.trim().split(" ").filter { it.isNotBlank() }
            .let { parts -> listOfNotNull(parts.firstOrNull(), parts.lastOrNull()) }
            .joinToString("") { it.first().uppercase() }
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Sobre o Autor",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(accent.color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = accent.onColor
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = autor,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = genero.split(",").first().trim(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = bio,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
