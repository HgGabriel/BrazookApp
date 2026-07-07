package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import kotlinx.coroutines.launch
import java.text.Normalizer
import kotlin.math.ceil

/**
 * `Series.episodios`/`temporadas` são totais da série inteira — não há episódios por temporada
 * no mock. Distribui o total igualmente entre temporadas, com a última absorvendo o resto
 * (ex. episodios=14, temporadas=2 → 7 e 7; episodios=11, temporadas=2 → 6 e 5).
 */
fun episodeCountForSeason(totalEpisodios: Int, totalTemporadas: Int, temporada: Int): Int {
    if (totalTemporadas <= 1) return totalEpisodios
    val perSeason = ceil(totalEpisodios.toDouble() / totalTemporadas).toInt()
    return if (temporada == totalTemporadas) {
        totalEpisodios - perSeason * (totalTemporadas - 1)
    } else {
        perSeason
    }
}

/** Deslocamento global (para chave de persistência) do primeiro episódio de [temporada]. */
private fun globalEpisodeOffset(totalEpisodios: Int, totalTemporadas: Int, temporada: Int): Int {
    if (totalTemporadas <= 1) return 0
    val perSeason = ceil(totalEpisodios.toDouble() / totalTemporadas).toInt()
    return perSeason * (temporada - 1)
}

private fun normalizeGenre(raw: String): String {
    val stripped = Normalizer.normalize(raw, Normalizer.Form.NFD).replace(Regex("\\p{M}"), "")
    return stripped.trim().lowercase()
}

private val episodeTitlesByGenre = mapOf(
    "crime" to listOf(
        "A Pista Inicial", "Sob Suspeita", "Rastros do Passado", "Cadeia de Mentiras",
        "O Informante", "Prova Circunstancial", "Silêncio Cúmplice", "A Confissão",
        "Jogo de Poder", "Sem Álibi", "A Testemunha", "Ponto Cego"
    ),
    "drama" to listOf(
        "Antes da Tempestade", "O Peso do Silêncio", "Entre Nós", "Feridas Abertas",
        "O Reencontro", "Sob a Superfície", "Promessas Quebradas", "O Preço da Verdade",
        "Depois do Adeus", "Laços de Sangue", "A Escolha", "Recomeço"
    ),
    "comedia" to listOf(
        "Um Dia Daqueles", "Plano Infalível", "Confusão à Vista", "Sem Filtro",
        "Malandragem", "De Mal a Pior", "A Armadilha Perfeita", "Improviso Total",
        "Última Chance", "Fora de Controle"
    ),
    "misterio" to listOf(
        "O Enigma", "Vozes na Névoa", "O Segredo Enterrado", "Pistas Falsas",
        "A Chave Perdida", "O Que Ninguém Viu", "Sombras do Passado", "O Terceiro Nome",
        "Labirinto", "A Revelação"
    ),
    "acao" to listOf(
        "Fuga Iminente", "Contra o Tempo", "Alvo Certo", "Sem Recuo",
        "Linha de Frente", "O Confronto", "Rota de Colisão", "Última Investida",
        "Sob Fogo", "Ponto de Ruptura"
    ),
    "aventura" to listOf(
        "Rumo ao Desconhecido", "A Travessia", "Mapa Perdido", "Terreno Hostil",
        "O Achado", "Encruzilhada", "Além da Fronteira", "A Trilha Certa",
        "Território Inimigo", "O Retorno"
    )
)

private val defaultEpisodeTitles = listOf(
    "Capítulo Novo", "Ponto de Virada", "O Próximo Passo", "Consequências",
    "Um Novo Começo", "Decisões", "O Que Restou", "Rumo ao Fim"
)

private fun episodeTitle(genero: String, localEpisodeNumber: Int): String {
    val primaryGenre = normalizeGenre(genero.split(",").first())
    val pool = episodeTitlesByGenre[primaryGenre] ?: defaultEpisodeTitles
    val phrase = pool[(localEpisodeNumber - 1) % pool.size]
    return "Episódio $localEpisodeNumber: $phrase"
}

/**
 * Lista vertical de episódios da temporada selecionada. Os títulos são gerados a partir do
 * gênero principal da série (não há episódio por episódio no mock); a numeração é local à
 * temporada, mas a chave de persistência de "visto" ([onToggleEpisode]) é global à série.
 */
@Composable
fun EpisodeListWidget(
    genero: String,
    totalEpisodios: Int,
    totalTemporadas: Int,
    temporada: Int,
    watchedEpisodes: Set<Int>,
    onToggleEpisode: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val count = remember(totalEpisodios, totalTemporadas, temporada) {
        episodeCountForSeason(totalEpisodios, totalTemporadas, temporada)
    }
    val offset = remember(totalEpisodios, totalTemporadas, temporada) {
        globalEpisodeOffset(totalEpisodios, totalTemporadas, temporada)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        for (local in 1..count) {
            val globalIndex = offset + local
            EpisodeRow(
                title = remember(genero, local) { episodeTitle(genero, local) },
                watched = watchedEpisodes.contains(globalIndex),
                onToggle = { onToggleEpisode(globalIndex) }
            )
        }
    }
}

@Composable
private fun EpisodeRow(
    title: String,
    watched: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = LocalCategoryAccent.current
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val indicatorScale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                coroutineScope.launch {
                    indicatorScale.animateTo(
                        1.25f,
                        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh)
                    )
                    indicatorScale.animateTo(
                        1f,
                        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
                    )
                }
                onToggle()
            }
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (watched) Icons.Rounded.CheckCircle else Icons.Rounded.RadioButtonUnchecked,
                contentDescription = if (watched) "Marcar como não visto" else "Marcar como visto",
                tint = if (watched) accent.color else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(26.dp)
                    .scale(indicatorScale.value)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = if (watched) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}
